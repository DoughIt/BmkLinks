package cn.fdsd.bmk.utils;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.domain.enums.RenderEnum;
import cn.fdsd.bmk.domain.enums.TreeSymbol;

/**
 * 树形结构工具类
 *
 * @author Jerry Zhang
 * create: 2022-11-01 19:30
 */
public class NodeUtil {
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
        out.append(node.getRenderContent(RenderEnum.DEFAULT));
    }

    private static void reverseWalkParent(StringBuilder out, Node node) {
        Node pNode = node.getParent();
        while (pNode != null) {
            out.insert(0, pNode.hasNextSibling() ? TreeSymbol.SPAN.getSymbol() : TreeSymbol.EMPTY.getSymbol());
            pNode = pNode.getParent();
        }
    }

    public static String toMdString(Node node) {
        StringBuilder sb = new StringBuilder();
        mdString(sb, node);
        return sb.toString();
    }

    private static void mdString(StringBuilder out, Node node) {
        out.append(node.getRenderContent(RenderEnum.MARKDOWN));
    }
}
