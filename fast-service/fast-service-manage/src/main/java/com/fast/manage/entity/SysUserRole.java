package com.fast.manage.entity;

import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户和角色关联对象 sys_user_role
 * 
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysUserRole extends BaseVersionEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private String userId;
    /** 角色ID */
    private String roleId;
    /** 类型（0代表后台 2代表API） */
    private String type;


}
