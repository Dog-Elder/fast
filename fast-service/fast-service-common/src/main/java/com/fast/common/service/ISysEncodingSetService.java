package com.fast.common.service;

import com.fast.common.entity.sys.SysEncodingSet;
import com.fast.common.query.SysEncodingSetQuery;
import com.fast.common.vo.SysEncodingSetVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 编码集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
public interface ISysEncodingSetService extends BaseService<SysEncodingSet> {

    List<SysEncodingSetVO> list(SysEncodingSetQuery query);

    List<SysEncodingSetVO> save(List<SysEncodingSetVO> vo);

    boolean update(SysEncodingSetVO vo);

    boolean delete(List<String> idList);
}