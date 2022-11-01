package cn.fdsd.bmk.ast.visitor;

import cn.fdsd.bmk.ast.Node;

/**
 * 通用的 visitor
 * @author Jerry Zhang
 * create: 2022-11-01 16:10
 */
public class GeneralVisitor<N extends Node> extends NodeAdaptedVisitor<N> {
    // 暂时不做任何处理
}
