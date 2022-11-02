package cn.fdsd.bmk.core.cmd.factory;

import cn.fdsd.bmk.core.cmd.GeneralCommand;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.domain.po.CommandPo;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令集中处理
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:04
 */
public class CommandInvoker {
    private Map<CommandEnum, GeneralCommand> commandMap;

    private static class InvokerHolder {
        private static final CommandInvoker INSTANCE = new CommandInvoker();
    }

    public static CommandInvoker getInstance() {
        return InvokerHolder.INSTANCE;
    }

    private CommandInvoker() {
        this.initCommands();
    }


    private void initCommands() {
        this.commandMap = new HashMap<>(8);
        for (CommandEnum commandEnum : CommandEnum.values()) {
            GeneralCommand command = CommandFactory.createCommand(commandEnum, true);
            commandMap.putIfAbsent(commandEnum, command);
        }
    }

    public void execute(Bookmark bookmark, CommandPo po) {
        if (po == null) {
            throw new CommandException(CommandErrorCode.PARSE_FAILED);
        }
        GeneralCommand command = commandMap.get(po.getName());
        if (command != null) {
            command.setBookmark(bookmark);
            command.setPo(po);
            command.execute();
        } else if (po.getName() == CommandEnum.UNDO || po.getName() == CommandEnum.REDO) {
            // todo 处理undo redo

        } else {
            throw new CommandException(CommandErrorCode.NOT_SUPPORT);
        }
    }
}
