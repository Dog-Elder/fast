package com.fast.core.common.util.tree.forest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fast.core.common.constant.Constants.ROOT_NODE_ID;


/**
 * 森林节点管理器
 *
 * @author panzhipeng@jk366.net
 * @since 2023/4/6 18:27
 */
public class ForestManager<T extends TreeNode<T>> {

    private final List<T> nodes;

    private final List<String> parentIds = new ArrayList<>();

    public ForestManager(List<T> nodes) {
        this.nodes = nodes;
    }

    /**
     * 根据节点id获取节点
     *
     * @param id 节点id
     * @return 对应的节点对象
     */
    public TreeNode<T> getNodeById(String id) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        return nodes.stream().filter(node -> node.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * 增加父节点id缓存
     *
     * @param parentId 父节点id
     */
    public void addParentId(String parentId) {
        parentIds.add(parentId);
    }

    /**
     * 获取树的根节点(一个森林对应多颗树)
     *
     * @return 树的根节点集合
     */
    public List<T> getRoots() {
        if (nodes == null || nodes.isEmpty()) {
            return new ArrayList<>();
        }
        return nodes.stream().filter(node -> ROOT_NODE_ID.equals(node.getParentId())
                || parentIds.contains(node.getId())).collect(Collectors.toList());
    }
}
