package com.fast.common.vo;

import com.fast.common.entity.verification.Qry;
import com.fast.common.entity.verification.Save;
import com.fast.common.entity.verification.Update;
import com.fast.core.common.annotation.lov.Lov;
import com.fast.core.common.domain.vo.Vo;
import com.fast.core.common.util.Com;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 编码集
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Data
public class SysEncodingSetVO extends Vo {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Display("id")
    @NotNull(message = Com.Require, groups = {Update.class})
    private String id;

    /**
     * 规则代码
     */
    @Display("规则代码")
    @NotBlank(message = Com.Require, groups = {Save.class, Update.class})
    private String sysEncodingCode;

    /**
     * 编码值
     */
    @Display("编码值")
    @NotBlank(message = Com.Require, groups = {Save.class, Update.class})
    private String sysEncodingSetCode;

    /**
     * 状态 Y :启用 N:停用
     */
    @Lov("STATUS")
    @Display("状态 Y :启用 N:停用 ")
    @NotBlank(message = Com.Require, groups = {Save.class, Update.class})
    private String sysEncodingSetStatus;

    /**
     * 是否已经使用 Y :已使用 N:未使用
     */
    @Lov("STATUS")
    @Display("是否已经使用 Y :已使用 N:未使用 ")
    @Null(message = Com.NotRequire, groups = {Save.class})
    private String sysEncodingSetUseStatus;


    /**
     * 创建者
     */
    @Display("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @Display("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Display("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @Display("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 版本
     */
    @Display("版本")
    private Integer version;

    /**
     * 备注信息
     */
    @Display("备注信息")
    @Size(max = Com.LongLen, message = Com.MaxLen, groups = {Save.class, Update.class})
    private String remark;


}