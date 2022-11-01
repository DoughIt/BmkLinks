package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.utils.PrinterTreeUtil;

/**
 * 打印 ls-tree 信息
 *
 * @author Jerry Zhang
 * create: 2022-11-01 16:08
 */
public class PrinterTreeVisitor<N extends Node> extends NodeAdaptedVisitor<N> {

    @Override
    public void visit(Title node) {
        System.out.println(PrinterTreeUtil.toTreeString(node));
        super.visit(node);
    }

    @Override
    public void visit(Link node) {
        System.out.println(PrinterTreeUtil.toTreeString(node));
        super.visit(node);
    }
}
