package com.fast.common.dao;

import com.fast.core.mybatis.dao.BaseDao;
import com.fast.common.entity.SysUserAuthentication;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户认证管理
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-08-22
*/
@Mapper
public interface SysUserAuthenticationDao extends BaseDao<SysUserAuthentication> {

}