package cn.fdsd.bmk.ast.node;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.NodeAdaptedVisitor;
import cn.fdsd.bmk.domain.enums.RenderEnum;
import cn.fdsd.bmk.utils.StringUtil;
import lombok.*;

import java.util.List;

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
    private Integer level;  // 标题级别，可以不设置
    private String text;    // 标题

    @Override
    public String getRenderContent(RenderEnum renderEnum) {
        if (renderEnum == RenderEnum.MARKDOWN) {
            return StringUtil.repeatStr("#", getLevel()) + " " + text;
        }
        return text;
    }

    /**
     * name 字段用于查询
     * @return
     */
    @Override
    public String getName() {
        return text;
    }

    @Override
    protected boolean keyEquals(Node a, Node b) {
        return a instanceof Title && b instanceof Title && (((Title) a).getText().equals(((Title) b).getText()));
    }

    @Override
    public <N extends Node> void accept(NodeAdaptedVisitor<N> nVisitor) {
        nVisitor.visit(this);
    }
}
