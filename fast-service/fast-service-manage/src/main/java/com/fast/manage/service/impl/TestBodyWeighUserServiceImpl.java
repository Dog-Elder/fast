package com.fast.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fast.core.common.util.PageUtils;
import com.fast.core.common.util.SUtil;
import com.fast.core.common.util.Util;
import com.fast.core.common.util.bean.BUtil;
import com.fast.core.mybatis.service.impl.BaseServiceImpl;
import com.fast.manage.dao.TestBodyWeighUserDao;
import com.fast.manage.entity.TestBodyWeighUser;
import com.fast.manage.query.TestBodyWeighUserQuery;
import com.fast.manage.service.ITestBodyWeighUserService;
import com.fast.manage.vo.TestBodyWeighUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 体重记录用户
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
@Service
@RequiredArgsConstructor
public class TestBodyWeighUserServiceImpl extends BaseServiceImpl<TestBodyWeighUserDao, TestBodyWeighUser> implements ITestBodyWeighUserService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<TestBodyWeighUserVO> list(TestBodyWeighUserQuery query) {
        List<TestBodyWeighUser> entityList = list(getWrapper(query));
        return PageUtils.copy(entityList,TestBodyWeighUserVO.class);
    }

    private LambdaQueryWrapper<TestBodyWeighUser> getWrapper(TestBodyWeighUserQuery query){
        LambdaQueryWrapper<TestBodyWeighUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Util.isNotNull(query.getId())&& SUtil.isNotEmpty(query.getId()), TestBodyWeighUser::getId, query.getId());
        wrapper.like(Util.isNotNull(query.getName())&& SUtil.isNotEmpty(query.getName()), TestBodyWeighUser::getName, query.getName());
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestBodyWeighUserVO> save(List<TestBodyWeighUserVO> vo) {
        List<TestBodyWeighUser> entityList = BUtil.copyList(vo,TestBodyWeighUser.class);
        saveBatch(entityList);
        return BUtil.copyList(entityList,TestBodyWeighUserVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(TestBodyWeighUserVO vo) {
        TestBodyWeighUser entity = BUtil.copy(vo, TestBodyWeighUser.class);
        return updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<String> idList) {
        return removeByIds(idList);
    }

    @Override
    public TestBodyWeighUser getInfo(String id) {
        TestBodyWeighUser user = getById(id);
        return null;
    }

}