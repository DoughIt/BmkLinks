package cn.fdsd.bmk.core.cmd;


/**
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class ShowCommand extends GeneralCommand{
    @Override
    public void execute() {
        switch (po.getName()) {
            case SHOW_TREE:
                // todo
                break;
            case LS_TREE:
                bookmark.lsTree();
                break;
            default:
                break;
        }
    }
}