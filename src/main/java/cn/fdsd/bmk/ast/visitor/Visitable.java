package cn.fdsd.bmk.ast.visitor;


import cn.fdsd.bmk.ast.Node;

public interface Visitable {
    <N extends Node> void accept(NodeAdaptedVisitor<N> nVisitor);
}
