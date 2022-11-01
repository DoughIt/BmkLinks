package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;


/**
 * @author Jerry Zhang
 * create: 2022-11-01 14:24
 */
public abstract class NodeAdaptedVisitor<N extends Node> implements Visitor<N> {
    private void visitChildren(final Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            if (next != null) {
                next.accept(this);
            }
            node = next;
        }
    }

    public void visit(final Title node) {
        visitChildren(node);
    }

    public void visit(final Link node) {
        // 不做处理
    }
}

