package cn.fdsd.bmk.core.cmd;


/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class DelCommand extends GeneralCommand {
    @Override
    public void execute() {
        switch (po.getName()) {
            case DEL_TITLE:
                bookmark.deleteDirectory(po.getArgs()[0]);
                break;
            case DEL_BOOKMARK:
                bookmark.deleteItem(po.getArgs()[0]);
                break;
            default:
                break;
        }
    }
}
