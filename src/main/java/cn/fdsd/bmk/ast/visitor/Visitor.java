package cn.fdsd.bmk.ast.visitor;


import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;

/**
 * 访问者接口
 * @author Jerry Zhang
 * create: 2022-11-01 14:20
 */
public interface Visitor<N extends Node> extends NodeAdaptingVisitor<N> {
    void visit(Title title);
    void visit(Link title);
}
