package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
 * 角色和菜单关联对象 sys_role_menu
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
@TableName(value = "sys_role_menu", excludeProperty = {PublicFieldConstant.ID})
public class SysRoleMenu extends BaseVersionEntity {

    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 菜单ID
     */
    private String menuId;
    /**
     * 用户类型类型
     */
    private String type;
}
