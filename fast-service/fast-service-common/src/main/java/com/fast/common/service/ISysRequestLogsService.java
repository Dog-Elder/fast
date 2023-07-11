package com.fast.common.service;

import com.fast.common.entity.sys.SysRequestLogs;
import com.fast.common.query.SysRequestLogsQuery;
import com.fast.common.vo.SysRequestLogsVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 接口请求日志
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-07-10
 */
public interface ISysRequestLogsService extends BaseService<SysRequestLogs> {

    List<SysRequestLogsVO> list(SysRequestLogsQuery query);

    boolean delete(List<String> idList);
}