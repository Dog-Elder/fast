package com.fast.common.service;

import com.fast.common.dto.SysCreateCodeDTO;
import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.enums.code.AttachEnum;
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

    /**
     * 获取编码
     *
     * @param req: 生成编码请求对象
     * @Date: 2022/9/25 22:23
     * @return: java.lang.String 编码
     **/
    String createCode(SysCreateCodeDTO req);

    /**
     * 获取编码
     **/
    String createCode(String encodingCode, String encodingSetCode);

    /**
     * 获取编码
     **/
    String createCode(AttachEnum attachEnum);
}