package com.fast.common.service;

import com.fast.common.dto.SysCreateCode;
import com.fast.common.entity.sys.SysEncodingSetRule;
import com.fast.common.query.SysEncodingSetRuleQuery;
import com.fast.common.vo.SysEncodingSetRuleVO;
import com.fast.core.mybatis.service.BaseService;

import java.util.List;

/**
 * 编码段
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
public interface ISysEncodingSetRuleService extends BaseService<SysEncodingSetRule> {

    List<SysEncodingSetRuleVO> list(SysEncodingSetRuleQuery query);

    SysEncodingSetRuleVO save(SysEncodingSetRuleVO vo);

    boolean update(SysEncodingSetRuleVO vo);

    boolean delete(List<String> idList);

    /**
     * 获取编码
     *
     * @param req: 生成编码请求对象
     * @Date: 2022/9/25 22:23
     * @return: java.lang.String 编码
     **/
    public String createCode(SysCreateCode req);
}