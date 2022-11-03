package cn.fdsd.bmk.core.cmd.ext;

/**
 * 需要实现 undo / redo 功能
 *
 * @author Jerry Zhang
 * create: 2022-11-02 10:51
 */
public interface Undoable {

    int undo();

    int redo();

    boolean canUndo();

    boolean canRedo();

}
