package cn.fdsd.bmk.core.cmd;


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
                bookmark.addDirectoryAt(ParserUtil.parseToTitle(po.getArgs()[0]), po.getAtOption());
                break;
            case ADD_BOOKMARK:
                bookmark.addItemAt(ParserUtil.parseToLink(po.getArgs()[0]), po.getAtOption());
                break;
            default:
                break;
        }
    }
}
