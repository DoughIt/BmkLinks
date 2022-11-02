package cn.fdsd.bmk.core.cmd.ext;

import cn.fdsd.bmk.core.cmd.Command;
import cn.fdsd.bmk.core.cmd.GeneralCommand;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 装饰器模式实现功能扩展
 *
 * @author Jerry Zhang
 * create: 2022-11-02 11:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class UndoableCommand extends GeneralCommand implements Undoable {
    private Command command;

    protected boolean canUndo;

    public UndoableCommand(Command command) {
        this.command = command;
    }

    public boolean canUndo() {
        return canUndo;
    }

    public boolean canRedo() {
        return !canUndo;
    }

    protected void command() {
        command.execute();
    }

    protected abstract void restore();

    public void execute() {
        command();
        setCanUndo(true);
    }

    protected void unExecute() {
        restore();
        setCanUndo(false);
    }

    public void undo() {
        if (!canUndo()) {
            throw new CommandException(CommandErrorCode.CANNOT_UNDO);
        }
        unExecute();
    }

    /**
     * 上一步为 undo 才可以 redo
     */
    public void redo() {
        if (!canRedo()) {
            throw new CommandException(CommandErrorCode.CANNOT_REDO);
        }
        execute();
    }
}
