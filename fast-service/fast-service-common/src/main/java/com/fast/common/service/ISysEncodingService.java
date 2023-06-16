package com.fast.common.service;

import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.query.SysEncodingQuery;
import com.fast.common.vo.SysEncodingVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 编码
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
public interface ISysEncodingService extends BaseService<SysEncoding> {

    List<SysEncodingVO> list(SysEncodingQuery query);

    List<SysEncodingVO> save(List<SysEncodingVO> vo);

    boolean update(SysEncodingVO vo);

}