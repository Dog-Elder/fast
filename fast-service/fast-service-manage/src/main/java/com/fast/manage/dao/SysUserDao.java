package com.fast.manage.dao;

import com.fast.core.mybatis.dao.BaseDao;
import com.fast.manage.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台用户Mapper接口
 * 
 * @author @Dog_Elder
 * @date 2021-06-18
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {

}
