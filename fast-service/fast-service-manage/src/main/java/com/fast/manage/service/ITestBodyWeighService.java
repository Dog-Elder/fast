package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.entity.TestBodyWeigh;
import com.fast.manage.query.TestBodyWeighQuery;
import com.fast.manage.vo.TestBodyWeighVO;

import java.util.List;

/**
 * 体重记录
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2024-02-20
 */
public interface ITestBodyWeighService extends BaseService<TestBodyWeigh> {

    List<TestBodyWeighVO> list(TestBodyWeighQuery query);

    List<TestBodyWeighVO> save(List<TestBodyWeighVO> vo);

    boolean update(TestBodyWeighVO vo);

    boolean delete(List<String> idList);

    TestBodyWeighVO saveOne(TestBodyWeighVO vo);
}