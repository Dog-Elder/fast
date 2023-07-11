package com.fast.common.dao;

import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.core.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 接口请求日志
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-07-10
*/
@Mapper
public interface SysRequestLogsDao extends BaseDao<SysRequestLogs> {

}