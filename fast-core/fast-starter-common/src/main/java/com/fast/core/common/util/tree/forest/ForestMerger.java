package com.fast.core.common.util.tree.forest;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static com.fast.core.common.constant.Constants.ROOT_NODE_ID;


/**
 * 森林节点合并器
 *
 * @author panzhipeng@jk366.net
 * @since 2023/4/6 18:47
 */
@Slf4j
public class ForestMerger {

    /**
     * 测试
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        List<ForestNode> list = new ArrayList<>();
        list.add(ForestNode.builder().id("1L").parentId("0L").value("节点1").build());
        list.add(ForestNode.builder().id("2L").parentId("1L").value("节点2").build());
        list.add(ForestNode.builder().id("3L").parentId("2L").value("节点3").build());
        list.add(ForestNode.builder().id("4L").parentId("3L").value("节点4").build());
        list.add(ForestNode.builder().id("5L").parentId("4L").value("节点5").build());
        list.add(ForestNode.builder().id("6L").parentId("3L").value("节点6").build());
        list.add(ForestNode.builder().id("7L").parentId("5L").value("节点7").build());
        list.add(ForestNode.builder().id("8L").parentId("5L").value("节点8").build());
        list.add(ForestNode.builder().id("9L").parentId("6L").value("节点9").build());
        list.add(ForestNode.builder().id("10").parentId("9L").value("节点10").build());
        List<ForestNode> result = merge(list);
        log.info("================ WebixForestNode 生成的树森林 =======================");
//         log.info(JSON.toJSONString(result, JSONWriter.Feature.PrettyFormat));

    }

    /**
     * 合并
     *
     * @param nodes 节点
     * @return {@link List}<{@link T}>
     */
    public static <T extends TreeNode<T>> List<T> merge(List<T> nodes) {
        ForestManager<T> forestManager = new ForestManager<>(nodes);

        nodes.forEach(item -> {
            String itemParentId = item.getParentId();
            if (!ROOT_NODE_ID.equals(itemParentId)) {
                TreeNode<T> node = forestManager.getNodeById(itemParentId);
                if (node == null) {
                    forestManager.addParentId(item.getId());
                } else {
                    node.getData().add(item);
                }
            }
        });

        return forestManager.getRoots();
    }


    /**
     * 在提供的所有节点中，将传入节点nodeId 的所有父级递归查找放入结果集result中
     *
     * @param allNodes 所有菜单
     * @param result   结果集
     * @param nodeId   目标节点id
     * @param <T>      节点类型
     */
    public static <T extends BaseNode<T>> void recursionParent(final List<T> allNodes, List<T> result, String nodeId) {
        if (ROOT_NODE_ID.equals(nodeId) || result.stream().anyMatch(r -> r.getId().equals(nodeId))) {
            return;
        }
        allNodes.stream().filter(m -> m.getId().equals(nodeId))
                .findFirst()
                .ifPresent(find -> {
                    result.add(find);
                    recursionParent(allNodes, result, find.getParentId());
                });
    }
    /**
     * 在提供的所有节点中，将传入节点nodeId 的所有子节点递归查找放入结果集result中
     * @param allNodes 所有菜单
     * @param result   结果集
     * @param nodeId   根目标节点id
     * @param <T>      节点类型
     */
    public static <T extends BaseNode<T>> void recursionChild(final List<T> allNodes, List<T> result, String nodeId) {
        if(ROOT_NODE_ID.equals(nodeId)){
            result.addAll(allNodes);
            return ;
        }
        if(allNodes.stream().noneMatch(n->n.getId().equals(nodeId))){
            return;
        }
        allNodes.stream().filter(m -> m.getParentId().equals(nodeId)).forEach(find -> {
            if (result.stream().noneMatch(r -> r.getId().equals(find.getId()))) {
                result.add(find);
            }
            recursionChild(allNodes, result, find.getId());
        });
    }
}
