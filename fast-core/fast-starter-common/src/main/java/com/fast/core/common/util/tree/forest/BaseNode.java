package com.fast.core.common.util.tree.forest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础节点实现
 *
 * @author panzhipeng@jk366.net
 * @since 2023/4/6 18:15
 */
@SuperBuilder
@Data
@NoArgsConstructor
public class BaseNode<T> implements TreeNode<T> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 父节点ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String parentId;

    /**
     * 子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Builder.Default
    private List<T> data = new ArrayList<>();

    /**
     * 是否有子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean hasChildren;

    /**
     * 是否有子孙节点
     *
     * @return Boolean
     */
    @Override
    public Boolean getHasChildren() {
        if (data.size() > 0) {
            return true;
        } else {
            return this.hasChildren;
        }
    }

}
