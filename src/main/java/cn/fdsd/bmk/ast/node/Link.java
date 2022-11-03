package cn.fdsd.bmk.ast.node;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import cn.fdsd.bmk.domain.enums.RenderEnum;
import lombok.*;

/**
 * funContent = [提示](url)
 *
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
    public String getRenderContent(RenderEnum renderEnum) {
        if (renderEnum == RenderEnum.MARKDOWN) {
            return "[" + text + "](" + url + ")";
        }
        return text + " " + url + " (已访问 " + getAccesses() + " 次)";
    }

    /**
     * name 字段用于查询
     *
     * @return
     */
    @Override
    public String getName() {
        return text;
    }

    @Override
    protected boolean keyEquals(Node a, Node b) {
        return a instanceof Link && b instanceof Link && ((Link) a).getText().equals(((Link) b).getText());
    }

    @Override
    public <N extends Node> void accept(NodeAdaptedVisitor<N> nVisitor) {
        nVisitor.visit(this);
    }

}
