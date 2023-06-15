package com.fast.common.query;


import com.fast.common.entity.sys.SysSetValue;
import com.fast.common.entity.verification.Qry;
import com.fast.core.common.constant.Constants;
import com.fast.core.common.domain.page.Query;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @description: 值集查询对象请求
 * @see SysSetValue
 **/
@Data
@Accessors(chain = true)
public class SysSetValueQuery extends Query {
    /**
     * 值集编码
     **/
    @Display("值集编码")
    @NotBlank(message = Com.Require, groups = {Qry.class})
    private String setCode;
    /**
     * 值集值key
     **/
    @Display("值集值key")
    private String setValueKey;
    /**
     * 值集含义
     **/
    @Display("值集含义")
    private String setValueValue;
    /**
     * 关联值集key
     **/
    @Display("关联值集key")
    private String setRelationKey;
    /**
     * 主要是为了区分 是通过缓存查询还是通过数据库查询 直接命中数据库默认为1  命中缓存穿为0
     **/
    @JsonIgnore
    private String db = Constants.Y;
}
