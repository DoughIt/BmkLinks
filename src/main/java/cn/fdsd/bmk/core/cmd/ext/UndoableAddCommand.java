package cn.fdsd.bmk.core.cmd.ext;

import cn.fdsd.bmk.core.cmd.AddCommand;

public class UndoableAddCommand extends UndoableCommand {

    UndoableAddCommand(AddCommand command) {
        super(command);
    }

    @Override
    protected void restore() {

    }
}
