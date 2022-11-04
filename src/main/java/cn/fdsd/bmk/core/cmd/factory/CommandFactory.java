package cn.fdsd.bmk.core.cmd.factory;

import cn.fdsd.bmk.core.cmd.*;
import cn.fdsd.bmk.core.cmd.ext.UndoableAddCommand;
import cn.fdsd.bmk.core.cmd.ext.UndoableDelCommand;
import cn.fdsd.bmk.domain.enums.CommandEnum;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:04
 */
public class CommandFactory {
    public static CommandInvoker createInvoker() {
        return CommandInvoker.getInstance();
    }

    public static GeneralCommand createCommand(CommandEnum commandEnum, boolean undoable) {
        switch (commandEnum) {
            case ADD:
            case ADD_TITLE:
            case ADD_BOOKMARK:
                return undoable ? new UndoableAddCommand(new AddCommand()) : new AddCommand();
            case DELETE:
            case DEL_TITLE:
            case DEL_BOOKMARK:
                return undoable ? new UndoableDelCommand(new DelCommand()) : new DelCommand();
            case OPEN:
            case EDIT:
            case READ:
            case READ_TITLE:
                return new OpenCommand();
            case SHOW:
            case SHOW_TREE:
            case LS_TREE:
            case HISTORY:
                return new ShowCommand();
            case SAVE:
                return new SaveCommand();
            case EXIT:
               return new ExitCommand();
            case HELP:
                return new HelperCommand();
        }
        return null;
    }
}
