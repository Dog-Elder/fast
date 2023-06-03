package com.fast.common.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fast.common.dao.SysSetMapper;
import com.fast.common.entity.sys.SysSet;
import com.fast.common.service.ISysSetService;
import com.fast.core.common.exception.CustomException;
import com.fast.core.common.util.CUtil;
import com.fast.core.common.util.SUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 值集Service业务层处理
 *
 * @author @Dog_Elder
 * @date 2022-03-24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysSetServiceImpl extends ServiceImpl<SysSetMapper, SysSet> implements ISysSetService {

    /**
     * 查询值集
     *
     * @param id 值集ID
     * @return 值集
     */
    @Override
    public SysSet selectById(Long id) {
        return baseMapper.selectSysSetById(id);
    }

    /**
     * 查询值集列表
     *
     * @param sysSet 值集
     * @return 值集
     */
    @Override
    public List<SysSet> list(SysSet sysSet) {
        return baseMapper.selectSysSetList(sysSet);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<SysSet> addSave(List<SysSet> sysSets) {
        //检查添加的数据中是否有重复编码数据
        Set<String> setCodes = sysSets.stream().map(SysSet::getSetCode).collect(Collectors.toSet());
        if (setCodes.size() != sysSets.size()) {
            throw new CustomException("输入的值集编码有重复.");
        }
        //检查添的数据中的编码是否已在数据库中存在
        String repeatCode = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getSetCode, setCodes).list().stream().map(SysSet::getSetCode).collect(Collectors.joining(","));
        if (SUtil.isNotEmpty(repeatCode)) {
            throw new CustomException("编码已存在:" + repeatCode);
        }
        //检查数据中的有父值集的是否存在
        Set<String> setParentCodes = sysSets.stream().filter(ele -> ele.getSetParentCode() != null).map(SysSet::getSetParentCode).collect(Collectors.toSet());
        if (CUtil.isNotEmpty(setParentCodes)) {
            List<SysSet> parentSet = new LambdaQueryChainWrapper<>(baseMapper).select(SysSet::getSetCode).in(SysSet::getSetCode, setParentCodes).list();
            //查找不同的元素
            Collection<String> diffByHashSet = CUtil.getDiffByHashSet(setParentCodes, parentSet.stream().map(SysSet::getSetCode).collect(Collectors.toSet()));
            if (CUtil.isEmpty(diffByHashSet)) {
                saveBatch(sysSets);
                return sysSets;
            }
            throw new CustomException("关联的值集编码不存在" + diffByHashSet);
        }
        saveBatch(sysSets);
        return sysSets;
    }


    /**
     * 修改值集
     *
     * @param sysSet 值集
     * @return 结果
     */
    @Transactional
    @Override
    public boolean update(SysSet sysSet) {
        List<SysSet> dbList = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getId, sysSet.getId()).list();
        dbList.forEach(ele -> {
            //TODO 作者:@Dog_Elder 2022/3/25 17:27 待办  缺少操作权限校验
            if ((!SUtil.isEmpty(sysSet.getSetParentCode()) && !SUtil.isEmpty(ele.getSetParentCode()))) {
                if (!sysSet.getSetParentCode().equals(ele.getSetParentCode())) {
                    throw new CustomException("不可更换父级值集编码");
                }
            }
            if (!ele.getSetCode().equals(sysSet.getSetCode())) {
                throw new CustomException("不可更换值集编码");
            }
        });
        return updateById(sysSet);
    }

    /**
     * 真删除值集对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteByIds(String ids) {
        List<String> idList = SUtil.splitToStrList(ids);
        List<SysSet> sysSets = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getId, idList).list();
        Set<String> setCodes = sysSets.stream().map(SysSet::getSetCode).collect(Collectors.toSet());
        if (CUtil.isEmpty(setCodes)) {
            return baseMapper.deleteSysSetByIds(Convert.toStrArray(ids));
        }
        List<SysSet> dbList = new LambdaQueryChainWrapper<>(baseMapper).in(SysSet::getSetParentCode, setCodes).list();
        StringBuilder sb = new StringBuilder();
        if (CUtil.isNotEmpty(dbList)) {
            sb.append("要删除的值集级有子集:");
            dbList.forEach(ele -> sb.append(ele.getSetParentCode()).append(","));
            sb.deleteCharAt(sb.length() - 1);
            throw new CustomException(sb.toString());
        }
        return baseMapper.deleteSysSetByIds(Convert.toStrArray(ids));
    }

    /**
     * 真删除值集信息
     *
     * @param id 值集ID
     * @return 结果
     */
    @Override
    public int deleteSysSetById(Long id) {
        return baseMapper.deleteSysSetById(id);
    }

    /**
     * 值集逻辑删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public boolean logicRemove(String ids) {
        return removeByIds(SUtil.splitToStrList(ids));
    }

}
