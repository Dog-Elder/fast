package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.constant.cache.CacheConstant;
import com.fast.common.dao.SysConfigDao;
import com.fast.common.entity.SysConfig;
import com.fast.common.query.SysConfigQuery;
import com.fast.common.service.ISysConfigService;
import com.fast.common.vo.SysConfigVO;
import com.fast.core.annotation.Cache;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.core.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.fast.common.constant.cache.CacheConstant.MANAGE;
import static com.fast.common.constant.cache.CacheConstant.UNLESS_RESULT_EQ_NULL;

/**
 * 系统参数配置
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-10
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigDao, SysConfig> implements ISysConfigService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysConfigVO> list(SysConfigQuery query) {
        List<SysConfig> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList, SysConfigVO.class);
    }

    private LambdaQueryWrapper<SysConfig> getWrapper(SysConfigQuery query) {
        LambdaQueryWrapper<SysConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId()) && SUtil.isNotEmpty(query.getId()), SysConfig::getId, query.getId());
        wrapper.like(Util.isNotNull(query.getParamName()) && SUtil.isNotEmpty(query.getParamName()), SysConfig::getParamName, query.getParamName());
        wrapper.like(Util.isNotNull(query.getParamKey()) && SUtil.isNotEmpty(query.getParamKey()), SysConfig::getParamKey, query.getParamKey());
        wrapper.eq(Util.isNotNull(query.getState()) && SUtil.isNotEmpty(query.getState()), SysConfig::getState, query.getState());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysConfigVO> save(List<SysConfigVO> vo) {
        List<SysConfig> entityList = BUtil.copyList(vo, SysConfig.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList, SysConfigVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysConfigVO vo) {
        CacheUtil.clearCache(MANAGE + "getParamValueByParamKey", vo.getParamKey());
        SysConfig entity = BUtil.copy(vo, SysConfig.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    @Override
    @Cache(value = CacheConstant.Config.VALUE, key = "#paramKey", unless = UNLESS_RESULT_EQ_NULL)
    public String getParamValueByParamKey(String paramKey) {
        SysConfig config = getOne(getWrapper(new SysConfigQuery().setState(Constants.Y).setParamKey(paramKey)));
        if (Util.isNotNull(config)) {
            return config.getParamValue();
        }
        return null;
    }

}