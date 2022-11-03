package cn.fdsd.bmk.core.cmd.factory;

import cn.fdsd.bmk.core.cmd.Command;
import cn.fdsd.bmk.core.cmd.GeneralCommand;
import cn.fdsd.bmk.core.cmd.ShowCommand;
import cn.fdsd.bmk.core.cmd.ext.UndoableCommand;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.domain.po.CommandPo;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.OutputUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


/**
 * 命令集中处理
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:04
 */
public class CommandInvoker {
    private final ArrayDeque<Command> history;
    private final ArrayDeque<UndoableCommand> undoHistory;

    private static class InvokerHolder {
        private static final CommandInvoker INSTANCE = new CommandInvoker();
    }

    public static CommandInvoker getInstance() {
        return InvokerHolder.INSTANCE;
    }

    private CommandInvoker() {
        history = new ArrayDeque<>();
        undoHistory = new ArrayDeque<>();
    }

    public void execute(Bookmark bookmark, CommandPo po) {
        if (po == null) {
            throw new CommandException(CommandErrorCode.PARSE_FAILED);
        }
        GeneralCommand command = CommandFactory.createCommand(po.getName(), true);
        if (command != null) {
            command.setBookmark(bookmark);
            command.setPo(po);
            if (po.getName().equals(CommandEnum.HISTORY)) {
                showHistory();
                history.push(command);
            } else if (command.execute() >= 0) {
                history.push(command);
            }
        } else if (po.getName() == CommandEnum.UNDO) {
            if (history.isEmpty()) {
                throw new CommandException(CommandErrorCode.EMPTY_HISTORY);
            }
            Command lastCommand = history.pop();
            // 跳过 ls-tree 与 show-tree 等无关紧要的 command
            while (lastCommand instanceof ShowCommand && !history.isEmpty()) {
                lastCommand = history.pop();
            }
            // undo 操作
            if (lastCommand instanceof UndoableCommand) {
                UndoableCommand undoableCommand = (UndoableCommand) lastCommand;
                if (undoableCommand.canUndo() && undoableCommand.undo() > 0) {
                    undoHistory.push(undoableCommand);
                } else {
                    history.push(undoableCommand);
                    throw new CommandException(CommandErrorCode.CANNOT_UNDO);
                }
            } else {
                history.push(lastCommand);
                throw new CommandException(CommandErrorCode.CANNOT_UNDO);
            }
        } else if (po.getName() == CommandEnum.REDO) {
            // redo 操作
            if (undoHistory.isEmpty()) {
                throw new CommandException(CommandErrorCode.EMPTY_HISTORY);
            }
            UndoableCommand undoableCommand = undoHistory.pop();
            if (undoableCommand.canRedo() && undoableCommand.redo() > 0) {
                history.push(undoableCommand);
            } else {
                undoHistory.push(undoableCommand);
                throw new CommandException(CommandErrorCode.CANNOT_REDO);
            }
        } else {
            throw new CommandException(CommandErrorCode.NOT_SUPPORT);
        }
    }

    private void showHistory() {
        StringBuilder out = new StringBuilder();
        List<String> firstToLast = new ArrayList<>();
        history.forEach(cmd -> {
            if (cmd instanceof GeneralCommand) {
                firstToLast.add(((GeneralCommand) cmd).getPo().getFullCmd());
            }
        });
        int idx = 1;
        for (int i = firstToLast.size() - 1; i >= 0; i--) {
            out.append(idx++).append(" ").append(firstToLast.get(i)).append("\n");
        }
        OutputUtil.println(out.toString());
    }
}
