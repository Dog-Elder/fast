package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.TestBodyWeighUser;
import com.fast.manage.query.TestBodyWeighUserQuery;
import com.fast.manage.vo.TestBodyWeighUserVO;

import java.util.List;

/**
 * 体重记录用户
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
public interface ITestBodyWeighUserService extends BaseService<TestBodyWeighUser> {

    List<TestBodyWeighUserVO> list(TestBodyWeighUserQuery query);

    List<TestBodyWeighUserVO> save(List<TestBodyWeighUserVO> vo);

    boolean update(TestBodyWeighUserVO vo);

    boolean delete(List<String> idList);

    TestBodyWeighUser getInfo(String id);
}