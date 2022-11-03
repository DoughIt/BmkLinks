package cn.fdsd.bmk.ast;


import java.util.Iterator;

/**
 * 迭代器
 *
 * @author Jerry Zhang
 * create: 2022-11-03 08:36
 */
public class NodeIterator implements Iterator<Node> {
    final Node firstNode;
    final Node lastNode;
    Node node;
    Node result;

    public NodeIterator(Node firstNode) {
        this(firstNode, null);
    }

    public NodeIterator(Node firstNode, Node lastNode) {
        if (firstNode == null)
            throw new NullPointerException();

        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.node = firstNode;
    }

    @Override
    public boolean hasNext() {
        return this.node != null;
    }

    @Override
    public Node next() {
        result = null;

        if (node == null) {
            throw new NullPointerException();
        }

        result = node;
        node = node.getNext();
        if (node == null || result == lastNode) node = null;
        return result;
    }

    @Override
    public void remove() {
        if (result == null) {
            throw new IllegalStateException("该节点已移除");
        }
        result.unlink();
        result = null;
    }

    public static final Iterator<Node> EMPTY = new Iterator<Node>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            return null;
        }
    };
}
