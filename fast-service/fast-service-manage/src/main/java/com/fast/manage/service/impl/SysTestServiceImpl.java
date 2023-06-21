package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.entity.SysTest;
import com.fast.manage.query.SysTestQuery;
import com.fast.manage.vo.SysTestVO;
import com.fast.manage.dao.SysTestDao;
import com.fast.manage.service.ISysTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.SUtil;

import java.util.List;

/**
 * 测试
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-21
 */
@Service
@RequiredArgsConstructor
public class SysTestServiceImpl extends BaseServiceImpl<SysTestDao, SysTest> implements ISysTestService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<SysTestVO> list(SysTestQuery query) {
        List<SysTest> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,SysTestVO.class);
    }

    private LambdaQueryWrapper<SysTest> getWrapper(SysTestQuery query){
        LambdaQueryWrapper<SysTest> wrapper = Wrappers.lambdaQuery();
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SysTestVO> save(List<SysTestVO> vo) {
        List<SysTest> entityList = BUtil.copyList(vo,SysTest.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,SysTestVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SysTestVO vo) {
        SysTest entity = BUtil.copy(vo,SysTest.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

}