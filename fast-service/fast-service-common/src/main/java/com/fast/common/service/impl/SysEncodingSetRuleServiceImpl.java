package com.fast.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.common.dao.SysEncodingSetRuleDao;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.service.ISysEncodingSetRuleService;
import com.fast.common.vo.SysEncodingSetRuleVO;
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
 * 编码段
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Service
@AllArgsConstructor
public class SysEncodingSetRuleServiceImpl extends BaseServiceImpl<SysEncodingSetRuleDao, SysEncodingSetRule> implements ISysEncodingSetRuleService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysEncodingSetRuleVO> list(SysEncodingSetRuleQuery query) {
        List<SysEncodingSetRule> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysEncodingSetRuleVO.class);
    }

    private LambdaQueryWrapper<SysEncodingSetRule> getWrapper(SysEncodingSetRuleQuery query){
        LambdaQueryWrapper<SysEncodingSetRule> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), SysEncodingSetRule::getId, query.getId());
        wrapper.eq(Util.isNotNull(query.getSysEncodingCode())&& SUtil.isNotEmpty(query.getSysEncodingCode()), SysEncodingSetRule::getSysEncodingCode, query.getSysEncodingCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetCode())&& SUtil.isNotEmpty(query.getSysEncodingSetCode()), SysEncodingSetRule::getSysEncodingSetCode, query.getSysEncodingSetCode());
        wrapper.eq(Util.isNotNull(query.getSysEncodingSetRuleNumber()), SysEncodingSetRule::getSysEncodingSetRuleNumber, query.getSysEncodingSetRuleNumber());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysEncodingSetRuleVO> save(List<SysEncodingSetRuleVO> vo) {
        List<SysEncodingSetRule> entityList = BUtil.copyList(vo,SysEncodingSetRule.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysEncodingSetRuleVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysEncodingSetRuleVO vo) {
        SysEncodingSetRule entity = BUtil.copy(vo,SysEncodingSetRule.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}