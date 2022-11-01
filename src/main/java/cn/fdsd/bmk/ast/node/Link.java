package cn.fdsd.bmk.ast.node;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import lombok.*;

/**
 * funContent = [提示](url)
 * @author Jerry Zhang
 * create: 2022-11-01 16:12
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Link extends Node {
    private String text;    // 提示
    private String url;    // 链接

    @Override
    public <N extends Node> void accept(NodeAdaptedVisitor<N> nVisitor) {
        nVisitor.visit(this);
    }

}
