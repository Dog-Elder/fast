package com.fast.service.entity.sys;

import com.fast.core.mybatis.base.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 值集对象 sys_set
 *
 * @author @Dog_Elder
 * @date 2022-03-24
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysSet extends BaseVersionEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 值集编码 */
    @NotBlank(message = "值集编码不能为空")
    private String setCode;

    /** 父值集编码 */
    private String setParentCode;

    /** 值集名称 */
    @NotBlank(message = "值集名称不能为空")
    private String setName;

    /** 值集状态（0代表关闭 1代表启用） */
    @NotBlank(message = "值机状态不能为空")
    private String setState;

    /** 是否分页（0代表关闭 1代表启用） */
    @NotBlank(message = "是否分页不能为空")
    private String setPage;

    /** 值集概要 */
    private String setDescribe;

    /** 操作权限（0代表不可操作 1代表管理员可操作 2代表所有人可操作） */
    @NotBlank(message = "操作状态不能为空")
    private String setOperate;


}
