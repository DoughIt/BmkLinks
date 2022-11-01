package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;

/**
 * 打印 ls-tree 信息
 *
 * @author Jerry Zhang
 * create: 2022-11-01 16:08
 */
public class PrinterTreeVisitor<N extends Node> extends NodeAdaptedVisitor<N> {
    @Override
    public void visit(Title node) {
        System.out.println("-|");
        System.out.println(node.getText());
        super.visit(node);
    }

    @Override
    public void visit(Link node) {
        System.out.println(node.getUrl());
        super.visit(node);
    }
}
