package com.fast.common.service;

import com.fast.common.entity.SysUserAuthentication;
import com.fast.common.query.SysUserAuthenticationQuery;
import com.fast.common.vo.SysUserAuthenticationVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 用户认证管理
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-22
 */
public interface ISysUserAuthenticationService extends BaseService<SysUserAuthentication> {

    List<SysUserAuthenticationVO> list(SysUserAuthenticationQuery query);

    List<SysUserAuthenticationVO> save(List<SysUserAuthenticationVO> vo);

    boolean update(SysUserAuthenticationVO vo);

    boolean delete(List<String> idList);

    SysUserAuthenticationVO getByCode(String code);
}