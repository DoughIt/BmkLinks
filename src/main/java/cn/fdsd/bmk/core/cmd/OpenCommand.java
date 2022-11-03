package cn.fdsd.bmk.core.cmd;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class OpenCommand extends GeneralCommand {
    @Override
    public void execute() {
        switch (po.getName()) {
            case OPEN:
                bookmark.init();
                break;
            case EDIT:
                bookmark.lsTree();
                break;
            case REDO:
                bookmark.accessItem(po.getArgs());
                break;
            case READ_TITLE:
                bookmark.accessDirectory(po.getArgs());
            default:
                break;
        }
    }
}
