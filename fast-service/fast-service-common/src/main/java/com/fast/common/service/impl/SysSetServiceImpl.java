package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysSetDao;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.query.SysSetQuery;
import com.fast.common.service.ISysSetService;
import com.fast.common.service.ISysSetValueService;
import com.fast.common.vo.SysSetVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.*;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 值集Service业务层处理
 *
 * @author 黄嘉浩
 * @date 2022-03-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSetServiceImpl extends BaseServiceImpl<SysSetDao, SysSet> implements ISysSetService {
    @Lazy
    @Resource
    private ISysSetValueService sysSetValueService;
    private final FastRedis redis;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysSetVO> list(SysSetQuery query) {
        List<SysSet> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysSetVO.class);
    }

    private LambdaQueryWrapper<SysSet> getWrapper(SysSetQuery query) {
        LambdaQueryWrapper<SysSet> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysSet::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSetCode()) && SUtil.isNotEmpty(query.getSetCode()), SysSet::getSetCode, query.getSetCode());
        wrapper.eq(Util.isNotNull(query.getSetParentCode()) && SUtil.isNotEmpty(query.getSetParentCode()), SysSet::getSetParentCode, query.getSetParentCode());
        wrapper.like(Util.isNotNull(query.getSetName()) && SUtil.isNotEmpty(query.getSetName()), SysSet::getSetName, query.getSetName());
        wrapper.like(Util.isNotNull(query.getSetDescribe()) && SUtil.isNotEmpty(query.getSetDescribe()), SysSet::getSetDescribe, query.getSetDescribe());
        wrapper.like(Util.isNotNull(query.getRemark()) && SUtil.isNotEmpty(query.getRemark()), SysSet::getRemark, query.getRemark());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<SysSetVO> save(List<SysSetVO> vo) {
        List<SysSet> entityList = BUtil.copyList(vo, SysSet.class);
        // 检查添加的数据中是否有重复编码数据
        Set<String> setCodes = entityList.stream().map(SysSet::getSetCode).collect(Collectors.toSet());
        if (setCodes.size() != entityList.size()) {
            throw new ServiceException("输入的值集编码有重复");
        }
        // 检查添的数据中的编码是否已在数据库中存在
        String repeatCode = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getSetCode, setCodes).list().stream().map(SysSet::getSetCode).collect(Collectors.joining(","));
        if (SUtil.isNotEmpty(repeatCode)) {
            throw new ServiceException("编码已存在:" + repeatCode);
        }
        // 检查数据中的有父值集的是否存在
        Set<String> setParentCodes = entityList.stream().filter(ele -> ele.getSetParentCode() != null).map(SysSet::getSetParentCode).collect(Collectors.toSet());
        if (CUtil.isNotEmpty(setParentCodes)) {
            List<SysSet> parentSet = new LambdaQueryChainWrapper<>(baseMapper).select(SysSet::getSetCode).in(SysSet::getSetCode, setParentCodes).list();
            // 查找不同的元素
            Collection<String> diffByHashSet = CUtil.getDiffByHashSet(setParentCodes, parentSet.stream().map(SysSet::getSetCode).collect(Collectors.toSet()));
            if (CUtil.isEmpty(diffByHashSet)) {
                saveBatch(entityList);
                return BUtil.copyList(entityList, SysSetVO.class);
            }
            throw new ServiceException("关联的值集编码不存在" + diffByHashSet);
        }
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysSetVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysSetVO vo) {
        SysSet entity = BUtil.copy(vo, SysSet.class);
        List<SysSet> dbList = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getId, entity.getId()).list();
        dbList.forEach(ele -> {
            // TODO 作者:黄嘉浩 2022/3/25 17:27 待办  缺少操作权限校验
            if ((!SUtil.isEmpty(entity.getSetParentCode()) && !SUtil.isEmpty(ele.getSetParentCode()))) {
                if (!entity.getSetParentCode().equals(ele.getSetParentCode())) {
                    throw new ServiceException("不可更换父级值集编码");
                }
            }
            if (!ele.getSetCode().equals(entity.getSetCode())) {
                throw new ServiceException("不可更换值集编码");
            }
        });
        boolean outcome = updateById(entity);
        // 关闭值集需要删除值集状态缓存
        if (outcome) setState(entity.getSetCode(), entity.getSetState());
        return outcome;
    }


    @Transactional
    @Override
    public boolean delete(List<String> idList) {
        List<SysSet> sysSets = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getId, idList).list();
        Set<String> setCodes = sysSets.stream().map(SysSet::getSetCode).collect(Collectors.toSet());
        List<SysSet> dbList = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getSetParentCode, setCodes).list();
        StringBuilder sb = new StringBuilder();
        if (CUtil.isNotEmpty(dbList)) {
            sb.append("需先删除的值集级子集:");
            dbList.forEach(ele -> sb.append(ele.getSetParentCode()).append(","));
            sb.deleteCharAt(sb.length() - 1);
            throw new ServiceException(sb.toString());
        }
        boolean removeSetValue = sysSetValueService.remove(Wrappers.<SysSetValue>lambdaQuery()
                .in(SysSetValue::getSetCode, setCodes));
        if (!removeSetValue) {
            return false;
        }
        boolean removeSysSets = removeByIds(idList);
        // 删除缓存操作
        setCodes.forEach(ele -> {
            if (removeSysSets) {
                redis.deleteKey(SUtil.format(CacheConstant.SetValue.SET_STATE, ele));
                redis.deleteHash(CacheConstant.SetValue.SET_VALUE, ele);
            }
        });
        return removeSysSets;
    }

    @Override
    public Boolean isEnableBySetCode(String setCode) {
        Boolean value = redis.getObject(SUtil.format(CacheConstant.SetValue.SET_STATE, setCode), Boolean.class);
        if (value != null) {
            return value;
        }
        SysSet set = getOne(Wrappers.<SysSet>lambdaQuery().eq(SysSet::getSetCode, setCode));
        if (Util.isNull(set)) {
            return false;
        }
        setState(set.getSetCode(), set.getSetState());
        value = SUtil.equals(set.getSetState(), Constants.Y);
        return value;
    }

    /**
     * 更新缓存 值集状态
     *
     * @param setCode:  值集编码
     * @param setState: 值集状态（0:关闭 1:启用）
     **/
    private void setState(String setCode, String setState) {
        redis.setObject(SUtil.format(CacheConstant.SetValue.SET_STATE, setCode), SUtil.equals(setState, Constants.Y));
    }
}
