package com.fast.core.common.util.tree.forest;

import java.util.List;

/**
 * 树节点
 *
 * @author 黄嘉浩
 * @date 2023-07-17 15:53
 **/
public interface TreeNode<T> {

    /**
     * 得到id
     *
     * @return {@link String}
     */
    String getId();


    /**
     * 父节点id
     *
     * @return 返回父级id
     */
    String getParentId();

    /**
     * 获取子孙节点
     *
     * @return 返回孩子节点
     */
    List<T> getData();

    /**
     * 是否有子孙节点
     *
     * @return Boolean
     */
    default Boolean getHasChildren() {
        return false;
    }

    /**
     * 节点扩展值
     *
     * @return 在组装不同类型树时用作区分，具体查看实现类定义
     */
    default String getNodeExtension() {
        return null;
    }

    /**
     * 节点排序
     *
     * @return int
     */
    default Integer getNodeOrder() {
        return 0;
    }
}
