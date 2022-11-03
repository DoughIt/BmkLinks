package cn.fdsd.bmk.core.cmd;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class OpenCommand extends GeneralCommand {
    @Override
    public int execute() {
        switch (po.getName()) {
            case OPEN:
            case EDIT:
                return bookmark.open(po.getArgs() != null ? po.getArgs()[0] : null);
            case READ:
                return bookmark.accessItem(po.getArgs());
            case READ_TITLE:
                return bookmark.accessDirectory(po.getArgs());
            default:
                return -1;
        }
    }
}
