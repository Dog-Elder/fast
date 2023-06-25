package com.fast.manage.service;

import com.fast.core.mybatis.service.BaseService;
import com.fast.manage.vo.SysAttachVO;
import com.fast.manage.query.SysAttachQuery;
import com.fast.manage.entity.SysAttach;

import java.util.List;

/**
 * 系统附件
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-26
 */
public interface ISysAttachService extends BaseService<SysAttach> {

    List<SysAttachVO> list(SysAttachQuery query);

    List<SysAttachVO> save(List<SysAttachVO> vo);

    boolean update(SysAttachVO vo);

    boolean delete(List<String> idList);
}