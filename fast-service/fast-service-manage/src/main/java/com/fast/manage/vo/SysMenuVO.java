package com.fast.manage.vo;

import com.fast.common.entity.verification.Save;
import com.fast.core.common.domain.vo.Vo;
import com.fast.core.common.util.Com;
import com.fast.core.common.util.tree.forest.BaseNode;
import com.fast.core.common.util.tree.forest.TreeNode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.fast.core.common.validate.annotation.Display;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表
 *
 * @author 黄嘉浩 1300286201@qq.com
 * @since 1.0.0 2023-06-12
 */
@Data
public class SysMenuVO extends Vo implements TreeNode<SysMenuVO> {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Display("菜单ID")
    private String id;

    /**
     * 菜单名称
     */
    @Display("菜单名称")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String name;

    /**
     * 父菜单ID
     */
    @Display("父菜单ID")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String parentId;

    /**
     * 显示顺序
     */
    @Display("显示顺序")
    private Integer orderNum;

    /**
     * 请求地址
     */
    @Display("请求地址")
    private String url;

    /**
     * 打开方式（menuItem页签 menuBlank新窗口）
     */
    @Display("打开方式（menuItem页签 menuBlank新窗口）")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String target;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @Display("菜单类型（M目录 C菜单 F按钮）")
    @NotBlank(message = Com.Require, groups = {Save.class})
    private String menuType;

    /**
     * 菜单状态（0隐藏 1显示）
     */
    @Display("菜单状态（0隐藏 1显示）")
    private String visible;

    /**
     * 是否刷新（0不刷新 1刷新）
     */
    @Display("是否刷新（0不刷新 1刷新）")
    private String isRefresh;

    /**
     * 权限标识
     */
    @Display("权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @Display("菜单图标")
    private String icon;

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
     * 备注
     */
    @Display("备注")
    private String remark;

    /**
     * 版本
     */
    @Display("版本")
    private Integer version;

    /**
     * id链，所有上级id集合，“,”分割
     */
    private String idChain;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SysMenuVO> data;

    /**
     * 是否有子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean hasChildren;

    @Override
    public List<SysMenuVO> getData() {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        return this.data;
    }

    @Override
    public Boolean getHasChildren() {
        return this.data != null && !data.isEmpty();
    }
    @Override
    public Integer getNodeOrder() {
        return orderNum;
    }
}