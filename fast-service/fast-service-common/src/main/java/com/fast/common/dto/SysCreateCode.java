package com.fast.common.dto;


import com.fast.common.entity.verification.Qry;
import com.fast.core.common.util.Com;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author 黄嘉浩
 * @description: 生成编码请求对象
 **/
@Data
@Accessors(chain = true)
public class SysCreateCode {
    @Display("规则代码")
    @NotBlank(message = Com.Require,groups = {Qry.class})
    private String sysEncodingCode;

    @Display("编码值")
    @NotBlank(message = Com.Require,groups = {Qry.class})
    private String sysEncodingSetCode;
}
