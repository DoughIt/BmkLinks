package cn.fdsd.bmk.core.cmd.ext;

import cn.fdsd.bmk.core.cmd.DelCommand;

/**
 * @author Jerry Zhang
 * create: 2022-11-02 13:02
 */
public class UndoableDelCommand extends UndoableCommand {

    public UndoableDelCommand(DelCommand command) {
        super(command);
    }

    @Override
    protected void restore() {
        // todo
    }
}
