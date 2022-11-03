package cn.fdsd.bmk.core.cmd;


import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.utils.ParserUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class AddCommand extends GeneralCommand {
    @Getter
    private List<Node> nodes = new ArrayList<>();

    @Override
    public int execute() {
        switch (po.getName()) {
            // todo 暂时不批量处理 args
            case ADD_TITLE:
                Title title = ParserUtil.parseToTitle(po.getArgs()[0]);
                title.setFullContent(po.getFullCmd());
                int ret1 = bookmark.addDirectoryAt(title, po.getAtOption());
                if (ret1 > 0) {
                    nodes.add(title);
                }
                return ret1;
            case ADD_BOOKMARK:
                Link link = ParserUtil.parseToLink(po.getArgs()[0]);
                link.setFullContent(po.getFullCmd());
                int ret2 = bookmark.addItemAt(link, po.getAtOption());
                if (ret2 > 0) {
                    nodes.add(link);
                }
                return ret2;
            default:
                return -1;
        }
    }
}
