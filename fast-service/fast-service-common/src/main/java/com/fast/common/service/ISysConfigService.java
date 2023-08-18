package com.fast.common.service;

import com.fast.common.entity.SysConfig;
import com.fast.common.query.SysConfigQuery;
import com.fast.common.vo.SysConfigVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 系统参数配置
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-08-10
 */
public interface ISysConfigService extends BaseService<SysConfig> {

    List<SysConfigVO> list(SysConfigQuery query);

    List<SysConfigVO> save(List<SysConfigVO> vo);

    boolean update(SysConfigVO vo);

    boolean delete(List<String> idList);

    String getParamValueByParamKey(String paramKey);
}