package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.utils.NodeUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 输出到 bmk 文件
 *
 * @author Jerry Zhang
 * create: 2022-11-01 16:08
 */
public class OutputMdVisitor<N extends Node> extends NodeAdaptedVisitor<N> {
    private final StringBuilder out;

    public OutputMdVisitor(StringBuilder out) {
        this.out = out;
    }

    @Override
    public void visit(Title node) {
        out.append(NodeUtil.toMdString(node)).append("\n");
        super.visit(node);
    }

    @Override
    public void visit(Link node) {
        out.append(NodeUtil.toMdString(node)).append("\n");
        super.visit(node);
    }
}
