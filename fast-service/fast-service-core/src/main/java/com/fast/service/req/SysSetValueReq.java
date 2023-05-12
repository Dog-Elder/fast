package com.fast.service.req;


import com.fast.core.common.constant.Constants;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import com.fast.service.entity.sys.SysSetValue;
import com.fast.service.entity.verification.Qry;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @description: 值集查询对象请求
 * @see SysSetValue
 **/
@Data
@Accessors(chain = true)
public class SysSetValueReq {

    @Display("值集编码")
    @NotBlank(message = Com.Require, groups = {Qry.class})
    private String setCode;

    @Display("值集值key")
    private String setValueKey;

    @Display("值集含义")
    private String setValueValue;

    @Display("关联值集key")
    private String setRelationKey;

    //主要是为了区分 是通过缓存查询还是通过数据库查询 直接命中数据库默认为0  命中缓存穿为2
    private String db = Constants.Y;
}
