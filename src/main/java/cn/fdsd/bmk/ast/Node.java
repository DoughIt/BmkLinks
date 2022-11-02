package cn.fdsd.bmk.ast;

import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import cn.fdsd.bmk.ast.visitor.Visitable;
import cn.fdsd.bmk.domain.enums.RenderEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
        if (sibling.next == null) {
            sibling.parent.lastChild = sibling;
        }
    }

    public void unlink() {
        if (this.prev != null) {
            this.prev.next = this.next;
        } else if (this.parent != null) {
            this.parent.firstChild = this.next;
        }
        if (this.next != null) {
            this.next.prev = this.prev;
        } else if (this.parent != null) {
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

    /**
     * 获取渲染后的文本
     *
     * @return
     */
    public String getRenderContent(RenderEnum renderEnum) {
        // 默认不处理
        return this.fullContent;
    }
}