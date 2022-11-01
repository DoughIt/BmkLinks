package cn.fdsd.bmk.utils;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.domain.enums.TreeSymbol;

/**
 * 树形结构工具类
 * @author Jerry Zhang
 * create: 2022-11-01 19:30
 */
public class PrinterTreeUtil {
    public static String toTreeString(Node node) {
        StringBuilder sb = new StringBuilder();
        treeString(sb, node);
        return sb.toString();
    }

    private static void treeString(StringBuilder out, Node node) {
        StringBuilder intentString = new StringBuilder();
        reverseWalkParent(intentString, node);
        out.append(intentString);
        out.append(node.hasNextSibling() ? TreeSymbol.FIRST.getSymbol() : TreeSymbol.LAST.getSymbol());
        out.append(node.getRenderContent());
    }

    private static void reverseWalkParent(StringBuilder out, Node node) {
        Node pNode = node.getParent();
        while (pNode != null) {
            out.insert(0, pNode.hasNextSibling() ? TreeSymbol.SPAN.getSymbol() : TreeSymbol.EMPTY.getSymbol());
            pNode = pNode.getParent();
        }
    }
}
