package com.fast.common.enums.code;

import com.fast.common.entity.sys.SysEncoding;
import com.fast.common.entity.sys.SysEncodingSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 附件枚举
 * @author 黄嘉浩
 */

@Getter
@AllArgsConstructor
public enum AttachEnum {
    SYS("MANAGE_ATTACH","SYS");
    /**
     * 编码代码
     * {@link SysEncoding#sysEncodingCode}
     */
    private final String encodingCode;

    /**
     * 编码集代码
     * {@link SysEncodingSet#sysEncodingSetCode}
     */
    private final String encodingSetCode;
}
