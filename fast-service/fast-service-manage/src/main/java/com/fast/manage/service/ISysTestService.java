package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.vo.SysTestVO;
import com.fast.manage.query.SysTestQuery;
import com.fast.manage.entity.SysTest;

import java.util.List;

/**
 * 测试
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-21
 */
public interface ISysTestService extends BaseService<SysTest> {

    List<SysTestVO> list(SysTestQuery query);

    List<SysTestVO> save(List<SysTestVO> vo);

    boolean update(SysTestVO vo);

    boolean delete(List<String> idList);
}