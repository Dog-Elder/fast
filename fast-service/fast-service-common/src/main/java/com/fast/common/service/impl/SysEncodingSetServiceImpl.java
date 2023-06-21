package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysEncodingSetDao;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.service.ISysEncodingService;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.exception.ServiceException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.FastRedis;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 编码集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Service
@RequiredArgsConstructor
public class SysEncodingSetServiceImpl extends BaseServiceImpl<SysEncodingSetDao, SysEncodingSet> implements ISysEncodingSetService {
    @Lazy
    @Resource
    private ISysEncodingService encodingService;
    private final ISysEncodingSetRuleService encodingSetRuleService;
    private final FastRedis redis;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysEncodingSetVO> list(SysEncodingSetQuery query) {
        List<SysEncodingSet> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysEncodingSetVO.class);
    }

    private LambdaQueryWrapper<SysEncodingSet> getWrapper(SysEncodingSetQuery query){
        LambdaQueryWrapper<SysEncodingSet> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysEncodingSet::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSysEncodingCode())&& SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncodingSet::getSysEncodingCode, query.getSysEncodingCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetCode())&& SUtil.isNotEmpty(query.getSysEncodingSetCode()), SysEncodingSet::getSysEncodingSetCode, query.getSysEncodingSetCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetStatus())&& SUtil.isNotEmpty(query.getSysEncodingSetStatus()), SysEncodingSet::getSysEncodingSetStatus, query.getSysEncodingSetStatus());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetUseStatus())&& SUtil.isNotEmpty(query.getSysEncodingSetUseStatus()), SysEncodingSet::getSysEncodingSetUseStatus, query.getSysEncodingSetUseStatus());
        wrapper.like(Util.isNotNull(query.getRemark()) && SUtil.isNotEmpty(query.getRemark()), SysEncodingSet::getRemark, query.getRemark());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEncodingSetVO> save(List<SysEncodingSetVO> vo) {
        List<SysEncodingSet> entityList = BUtil.copyList(vo,SysEncodingSet.class);
        if (CUtil.toGrouping(entityList, SysEncodingSet::getSysEncodingCode).keySet().size() > 1) {
            throw new ServiceException("批量添加只能添加相同<规则代码>的编码集");
        }
        //获取编码 规则代码
        String sysEncodingCode = entityList.get(0).getSysEncodingCode();
        SysEncoding encoding = encodingService.getOne(new QueryWrapper<SysEncoding>().lambda().eq(SysEncoding::getSysEncodingCode, sysEncodingCode));
        Util.isNull(encoding, "编码不存在");
        List<String> setCodes = CUtil.getPropertyList(entityList.stream(), SysEncodingSet::getSysEncodingSetCode);
        List<String> duplicateElements = CUtil.getDuplicateElements(setCodes);
        if (CUtil.isNotEmpty(duplicateElements)) {
            throw new ServiceException("编码集的编码值重复:" + duplicateElements);
        }
        List<SysEncodingSet> existing = list(new QueryWrapper<SysEncodingSet>().lambda()
                .select(SysEncodingSet::getSysEncodingSetCode)
                .eq(SysEncodingSet::getSysEncodingCode, sysEncodingCode)
                .in(SysEncodingSet::getSysEncodingSetCode, setCodes)
        );
        if (CUtil.isNotEmpty(existing)) {
            Set<String> propertySet = CUtil.getPropertySet(existing, SysEncodingSet::getSysEncodingSetCode);
            throw new ServiceException("编码值" + propertySet + "已存在");
        }
        entityList.forEach(ele->{
            //添加时必须更新为null
            ele.setSysEncodingSetStatus(Constants._N);
            updateState(ele);
        });
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysEncodingSetVO.class);
    }

    private void updateState(SysEncodingSet set) {
        String ruleStatusIn = SUtil.format(CacheConstant.SysSetRule._STATUS, set.getSysEncodingCode(), set.getSysEncodingSetCode());
        redis.setString(ruleStatusIn, set.getSysEncodingSetStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingSetVO vo) {
        SysEncodingSet entity = BUtil.copy(vo,SysEncodingSet.class);
        SysEncodingSet encodingSet = super.getById(entity.getId());
        //是否已经使用 Y :已使用 时只能修改备注或状态
        if (SUtil.isY(encodingSet.getSysEncodingSetUseStatus())) {
            encodingSet.setRemark(entity.getRemark());
            encodingSet.setSysEncodingSetStatus(entity.getSysEncodingSetStatus());
            updateState(encodingSet);
            return updateById(encodingSet);
        }
        //未使用主要是判断是否有重名的编码规则
        SysEncodingSet exist = super.getOne(new QueryWrapper<SysEncodingSet>().lambda()
                .eq(SysEncodingSet::getSysEncodingCode, entity.getSysEncodingCode())
                .eq(SysEncodingSet::getSysEncodingSetCode, entity.getSysEncodingSetCode())
                //不是当前id
                .ne(SysEncodingSet::getId, entity.getId())
        );
        Util.isNotNull(exist, "编码已存在");
        if (SUtil.isY(entity.getSysEncodingSetStatus())) {
            //查询是否有编码段
            long count = encodingSetRuleService.count(new LambdaQueryWrapper<SysEncodingSetRule>()
                    .eq(SysEncodingSetRule::getSysEncodingCode, entity.getSysEncodingCode())
                    .eq(SysEncodingSetRule::getSysEncodingSetCode, entity.getSysEncodingSetCode()));
            if (count==0) {
                throw new ServiceException("未配置编码段,不能开启");
            }
        }
        updateState(entity);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        isEmploy(idList);
        return removeByIds(idList);
    }

    private void isEmploy(List<String> ids) {
        if (CUtil.isEmpty(ids)) {
            throw new ServiceException("未选中数据");
        }
        List<SysEncodingSet> list = list(new QueryWrapper<SysEncodingSet>().lambda()
                .select(SysEncodingSet::getSysEncodingSetCode, SysEncodingSet::getSysEncodingCode, SysEncodingSet::getSysEncodingSetCode)
                .in(SysEncodingSet::getId, ids)
                .eq(SysEncodingSet::getSysEncodingSetUseStatus, Constants._Y)
        );
        if (CUtil.isEmpty(list)) {
            list.forEach(this::updateState);
            return;
        }
        Set<String> propertySet = CUtil.getPropertySet(list, SysEncodingSet::getSysEncodingSetCode);
        throw new ServiceException("数据已被使用无法删除:" + propertySet);
    }

}