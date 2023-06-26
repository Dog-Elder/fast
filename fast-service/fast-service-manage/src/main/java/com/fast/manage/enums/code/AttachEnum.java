package com.fast.manage.enums.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 附件枚举
 * @author 黄嘉浩
 */

@Getter
@AllArgsConstructor
public enum AttachEnum {
    SYS(",","");
    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String policy;
}
