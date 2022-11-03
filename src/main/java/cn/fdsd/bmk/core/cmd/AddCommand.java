package cn.fdsd.bmk.core.cmd;


import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.utils.ParserUtil;
import lombok.AllArgsConstructor;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
@AllArgsConstructor
public class AddCommand extends GeneralCommand {
    @Override
    public void execute() {
        switch (po.getName()) {
            // todo 暂时不批量处理 args
            case ADD_TITLE:
                Title title = ParserUtil.parseToTitle(po.getArgs()[0]);
                title.setFullContent(po.getFullCmd());
                bookmark.addDirectoryAt(title, po.getAtOption());
                break;
            case ADD_BOOKMARK:
                Link link = ParserUtil.parseToLink(po.getArgs()[0]);
                link.setFullContent(po.getFullCmd());
                bookmark.addItemAt(link, po.getAtOption());
                break;
            default:
                break;
        }
    }
}
