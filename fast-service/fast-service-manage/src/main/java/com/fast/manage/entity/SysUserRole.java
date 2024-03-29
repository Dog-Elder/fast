package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fast.core.mybatis.constant.PublicFieldConstant;
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
 * @author 黄嘉浩
 * @date 2021-06-29
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "sys_user_role", excludeProperty = {PublicFieldConstant.ID})
public class SysUserRole extends BaseVersionEntity {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 类型（0代表后台 2代表API）
     */
    private String type;


}
