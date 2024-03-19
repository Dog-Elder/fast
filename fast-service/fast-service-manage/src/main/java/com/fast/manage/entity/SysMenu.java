package com.fast.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.validate.annotation.Display;
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
 * @author 黄嘉浩
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
     * 菜单名称
     */
    @Display("菜单名称")
    @TableField("name")
    private String name;

    /**
     * 父菜单ID
     */
    @Display("父菜单ID")
    @TableField("parent_id")
    private String parentId;

    /**
     * 显示顺序
     */
    @Display("显示顺序")
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 请求地址
     */
    @Display("请求地址")
    @TableField("url")
    private String url;

    /**
     * 打开方式（menuItem页签 menuBlank新窗口）
     */
    @Display("打开方式（menuItem页签 menuBlank新窗口）")
    @TableField("target")
    private String target;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Display("菜单类型（M目录 C菜单 F按钮）")
    @TableField("menu_type")
    private String menuType;

    /**
     * 菜单状态（0隐藏 1显示）
     */
    @Display("菜单状态（0隐藏 1显示）")
    @TableField("visible")
    private String visible;

    /**
     * 是否刷新（0不刷新 1刷新）
     */
    @Display("是否刷新（0不刷新 1刷新）")
    @TableField("is_refresh")
    private String isRefresh;

    /**
     * 权限标识
     */
    @Display("权限标识")
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @Display("菜单图标")
    @TableField("icon")
    private String icon;

}
