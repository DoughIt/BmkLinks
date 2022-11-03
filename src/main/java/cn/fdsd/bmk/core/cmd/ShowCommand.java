package cn.fdsd.bmk.core.cmd;


/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class ShowCommand extends GeneralCommand {
    @Override
    public int execute() {
        switch (po.getName()) {
            case SHOW_TREE:
                return bookmark.showTree();
            case LS_TREE:
                return bookmark.lsTree();
            case HISTORY:
                return 1;
            default:
                return -1;
        }
    }
}