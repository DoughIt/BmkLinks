package cn.fdsd.bmk.ast;


import java.util.Iterator;

/**
 * 可迭代接口
 *
 * @author Jerry Zhang
 * create: 2022-11-03 08:25
 */
public class NodeIterable implements Iterable<Node> {
    final Node firstNode;
    final Node lastNode;

    public NodeIterable(Node firstNode, Node lastNode) {
        this.firstNode = firstNode;
        this.lastNode = lastNode;
    }

    @Override
    public Iterator<Node> iterator() {
        return new NodeIterator(firstNode, lastNode);
    }

    public static final Iterable<Node> EMPTY = () -> NodeIterator.EMPTY;
}
