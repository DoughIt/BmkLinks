package cn.fdsd.bmk.ast;

import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import cn.fdsd.bmk.ast.visitor.Visitable;
import cn.fdsd.bmk.domain.enums.RenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 节点基本信息
 *
 * @author Jerry Zhang
 * create: 2022-11-01 14:10
 */
@Data
@EqualsAndHashCode
public abstract class Node implements Visitable, Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid = UUID.randomUUID().toString();   // 唯一 uuid
    private String name;            // 不是唯一的，用于查询一类节点
    private String fullContent;   // add 命令后的文字
    private Node parent = null;
    private Node firstChild = null; // 第一个直接子节点
    private Node lastChild = null;  // 最后一个子节点
    private Node prev = null;       // 上一个兄弟节点
    private Node next = null;       // 下一个兄弟节点

    private Boolean selected;       // 是否选中

    private Boolean accessed;       // 是否访问过

    private int accesses;           // 访问次数

    /**
     * 插入一个子节点到末尾
     *
     * @param child
     */
    public void appendChild(Node child) {
        child.unlink();
        child.setParent(this);
        if (this.lastChild != null) {
            this.lastChild.next = child;
            child.prev = this.lastChild;
        } else {
            this.firstChild = child;
        }
        this.lastChild = child;
    }

    /**
     * 在当前节点（含）及下属节点下查找 name 节点
     * 在 name 节点下插入子节点
     *
     * @param name
     * @param nameType
     * @param child
     */
    public void appendChildInNameNode(String name, Class<? extends Node> nameType, Node child) {
        // 检查 uuid 是否相同，避免新插入节点的 name 与 name 相同时进入无限循环
        if (getName() != null && getName().equals(name) &&
                nameType.isInstance(this) && !getUuid().equals(child.getUuid())) {
            appendChild(child);
        }
        for (Node node : getChildren()) {
            node.appendChildInNameNode(name, nameType, child);
        }
    }


    /**
     * 插入一个兄弟节点
     *
     * @param sibling
     */
    public void insertAfter(Node sibling) {
        sibling.unlink();
        sibling.next = this.next;
        if (sibling.next != null) {
            sibling.next.prev = sibling;
        }
        sibling.prev = this;
        this.next = sibling;
        sibling.parent = this.parent;
        if (sibling.next == null && sibling.parent != null) {
            sibling.parent.lastChild = sibling;
        }
    }

    /**
     * 在当前节点（含）及下属节点下查找 name 节点
     * 在 name 节点后插入兄弟节点
     *
     * @param name
     * @param nameType
     * @param sibling
     */
    public void insertAfterInNameNode(String name, Class<? extends Node> nameType, Node sibling) {
        // 检查 uuid 是否相同，避免新插入节点的 name 与 name 相同时进入无限循环
        if (getName() != null && getName().equals(name) &&
                nameType.isInstance(this) && !getUuid().equals(sibling.getUuid())) {
            insertAfter(sibling);
        }
        for (Node node : getChildren()) {
            node.insertAfterInNameNode(name, nameType, sibling);
        }
    }

    public void removeNameNodes(String name, Class<? extends Node> nameType, List<Node> deleteNodes) {
        if (getName() != null && getName().equals(name) && nameType.isInstance(this)) {
            // 保留 parent 信息，以便恢复
            Node pNode = this.parent;
            unlink();
            this.parent = pNode;
            deleteNodes.add(this);
        }
        for (Node node : getChildren()) {
            node.removeNameNodes(name, nameType, deleteNodes);
        }
    }

    public void removeNodesByUuids(List<String> uuids) {
        for (String id : uuids) {
            if (getUuid() != null && getUuid().equals(id)) {
                // 保留 parent 信息，以便恢复
                Node pNode = this.parent;
                unlink();
                this.parent = pNode;
            }
        }
        for (Node node : getChildren()) {
            node.removeNodesByUuids(uuids);
        }
    }

    public void unlink() {
        if (this.prev != null) {
            this.prev.next = this.next;
        } else if (this.parent != null && this.parent.firstChild == this) {
            this.parent.firstChild = this.next;
        }
        if (this.next != null) {
            this.next.prev = this.prev;
        } else if (this.parent != null && this.parent.lastChild == this) {
            this.parent.lastChild = this.prev;
        }
        this.parent = null;
        this.next = null;
        this.prev = null;
    }

    /**
     * 从当前节点开始遍历
     *
     * @param visitor
     */
    public void walk(NodeAdaptedVisitor<Node> visitor) {
        this.accept(visitor);
    }

    public int getDepths() {
        int depth = 0;
        Node pNode = this.parent;
        while (pNode != null) {
            depth++;
            pNode = pNode.getParent();
        }
        return depth;
    }

    public boolean hasNextSibling() {
        return this.next != null;
    }

    public boolean hasChildren() {
        return this.firstChild != null;
    }

    /**
     * 获取渲染后的文本
     *
     * @return
     */
    public String getRenderContent(RenderEnum renderEnum) {
        // 默认不处理
        return this.fullContent;
    }

    /**
     * 判断两个节点相等的基准
     *
     * @param a
     * @param b
     * @return
     */
    protected abstract boolean keyEquals(Node a, Node b);


    public List<Node> getNodesByKey() {
        return new ArrayList<>();
    }


    /**
     * 更新节点状态
     *
     * @param arg
     * @param isAccess
     * @param nameType
     */
    public void updateStatus(String arg, boolean isAccess, Class<? extends Node> nameType) {
        if (getName() != null && getName().equals(arg) && nameType.isInstance(this)) {
            if (isAccess) {
                updateAccess();
            } else {
                updateSelected();
            }
        }
        for (Node node : getChildren()) {
            node.updateStatus(arg, isAccess, nameType);
        }
    }

    private void updateAccess() {
        this.accessed = true;
        this.accesses++;
    }

    private void updateSelected() {
        this.selected = !this.selected;
    }

    /**
     * 统计节点上有多少指定类型的祖先节点
     *
     * @param nodeClass
     * @return
     */
    public int countAncestorsOfType(Class<? extends Node> nodeClass) {
        Node pNode = getParent();
        int count = 0;
        while (pNode != null) {
            if (nodeClass.isInstance(pNode)) {
                count++;
            }
            pNode = pNode.getParent();
        }
        return count;
    }

    /**
     * 统计节点下有多少指定类型的子节点
     *
     * @param nodeClass
     * @return
     */
    public int countChildrenOfType(Class<? extends Node> nodeClass) {
        int count = 0;
        for (Node child : getChildren()) {
            if (nodeClass.isInstance(child)) {
                count++;
            }
        }
        return count;
    }

    public Iterable<Node> getChildren() {

        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new NodeIterable(firstChild, lastChild);
    }

}