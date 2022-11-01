package cn.fdsd.bmk.ast.node;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import lombok.*;

/**
 * h1, h2, h3, h4, h5等标题
 * fullContent = # 标题
 *
 * @author Jerry Zhang
 * create: 2022-11-01 16:11
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Title extends Node {
    private Integer level;  // 标题级别
    private String text;    // 标题

    @Override
    public <N extends Node> void accept(NodeAdaptedVisitor<N> nVisitor) {
        nVisitor.visit(this);
    }
}
