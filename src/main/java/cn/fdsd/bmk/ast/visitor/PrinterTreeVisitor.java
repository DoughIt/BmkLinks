package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.utils.NodeUtil;

/**
 * 打印 ls-tree 信息
 *
 * @author Jerry Zhang
 * create: 2022-11-01 16:08
 */
public class PrinterTreeVisitor<N extends Node> extends NodeAdaptedVisitor<N> {
    private final StringBuilder out;

    public PrinterTreeVisitor(StringBuilder out) {
        this.out = out;
    }

    @Override
    public void visit(Title node) {
        out.append(NodeUtil.toTreeString(node)).append("\n");
        super.visit(node);
    }

    @Override
    public void visit(Link node) {
        out.append(NodeUtil.toTreeString(node)).append("\n");
        super.visit(node);
    }
}
