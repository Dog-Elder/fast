package com.fast.core.common.util.tree.forest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 森林节点实现
 *
 * @author panzhipeng@jk366.net
 * @since 2023/4/6 18:22
 */
@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ForestNode extends BaseNode<ForestNode> {

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点扩展值
     */
    private String nodeExtension;

    /**
     * 节点顺序
     */
    private Integer nodeOrder;

    @Override
    public Integer getNodeOrder() {
        return this.nodeOrder;
    }

    @Override
    public String getNodeExtension() {
        return this.nodeExtension;
    }
}
