package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色对象 sys_role
 *
 * @author 黄嘉浩
 * @date 2021-06-29
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class SysRole extends BaseVersionEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色权限字符串
     */
    @TableField("`key`")
    private String key;
    /**
     * 显示顺序
     */
    private Long sort;
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private String dataScope;
    /**
     * 角色状态（0:关闭 1:启用）
     */
    @TableField("`status`")
    private String status;


}
