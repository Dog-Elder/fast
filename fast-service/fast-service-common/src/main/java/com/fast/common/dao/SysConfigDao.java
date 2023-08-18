package com.fast.common.dao;

import com.fast.core.mybatis.dao.BaseDao;
import com.fast.common.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统参数配置
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-08-10
*/
@Mapper
public interface SysConfigDao extends BaseDao<SysConfig> {

}