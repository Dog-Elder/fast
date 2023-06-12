package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.dao.SysEncodingSetDao;
import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.service.ISysEncodingSetService;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 编码集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Service
@AllArgsConstructor
public class SysEncodingSetServiceImpl extends BaseServiceImpl<SysEncodingSetDao, SysEncodingSet> implements ISysEncodingSetService {

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
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysEncodingSetVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingSetVO vo) {
        SysEncodingSet entity = BUtil.copy(vo,SysEncodingSet.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}