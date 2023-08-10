package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.entity.SysConfig;
import com.fast.manage.query.SysConfigQuery;
import com.fast.manage.vo.SysConfigVO;
import com.fast.manage.dao.SysConfigDao;
import com.fast.manage.service.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.SUtil;

import java.util.List;

/**
 * 系统参数配置
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-09
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfig> implements ISysConfigService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysConfigVO> list(SysConfigQuery query) {
        List<SysConfig> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysConfigVO.class);
    }

    private LambdaQueryWrapper<SysConfig> getWrapper(SysConfigQuery query){
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysConfig::getId, query.getId());
        wrapper.like(Util.isNotNull(query.getParamName()) && SUtil.isNotEmpty(query.getParamName()), SysConfig::getParamName, query.getParamName());
        wrapper.like(Util.isNotNull(query.getParamKey()) && SUtil.isNotEmpty(query.getParamKey()), SysConfig::getParamKey, query.getParamKey());
        wrapper.eq(Util.isNotNull(query.getState())&& SUtil.isNotEmpty(query.getState()), SysConfig::getState, query.getState());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysConfigVO> save(List<SysConfigVO> vo) {
        List<SysConfig> entityList = BUtil.copyList(vo,SysConfig.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysConfigVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysConfigVO vo) {
        SysConfig entity = BUtil.copy(vo,SysConfig.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}