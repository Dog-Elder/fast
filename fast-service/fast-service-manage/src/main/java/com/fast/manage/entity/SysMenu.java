package com.fast.manage.entity;

import com.fast.core.mybatis.model.BaseVersionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 菜单权限对象 sys_menu
 *
 * @author @Dog_Elder
 * @date 2021-06-29
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysMenu extends BaseVersionEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 显示顺序
     */
    private Long orderNum;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 打开方式（menuItem页签 menuBlank新窗口）
     */

    private String target;
    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */

    private String menuType;
    /**
     * 菜单状态（0显示 1隐藏）
     */

    private String visible;
    /**
     * 是否刷新（0刷新 1不刷新）
     */
    private String isRefresh;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;
    /** 类型（0代表后台 2代表API） */
    private String type;


}
