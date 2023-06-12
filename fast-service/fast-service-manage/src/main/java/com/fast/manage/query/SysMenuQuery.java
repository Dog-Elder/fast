package com.fast.manage.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fast.core.common.validate.annotation.Display;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
* 菜单权限表查询
*
* @author 黄嘉浩 1300286201@qq.com
* @since 1.0.0 2023-06-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenuQuery {
    /**
     * 菜单ID
     */
    @Display("菜单ID")
    @TableField("id")
    private String id;

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

}