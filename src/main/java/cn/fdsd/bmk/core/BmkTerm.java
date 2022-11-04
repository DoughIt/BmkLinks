package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;
import cn.fdsd.bmk.domain.cont.Constants;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.domain.po.CommandPo;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.HintScanner;
import cn.fdsd.bmk.utils.OutputUtil;
import cn.fdsd.bmk.utils.ParserUtil;

import java.io.IOException;


public class BmkTerm {

    public static void main(String[] args) {
        // 监听输入、处理命令、打印输出
        CommandInvoker invoker = CommandFactory.createInvoker();
        Bookmark bookmark = new Bookmark(Constants.RESOURCE_PATH + "data.bmk");
        // 打印帮助文档
        invoker.execute(bookmark, CommandPo.builder().name(CommandEnum.HELP).fullCmd("help").build());
        // 初始化
        if (args.length > 0) {
            bookmark.setPath(args[0]);
            bookmark.init();
        }
        // 监听
        try (HintScanner scanner = new HintScanner(System.in)) {
            String cmd;
            while ((cmd = scanner.nextLine(Constants.HINT, false)) != null) {
                try {
                    invoker.execute(bookmark, ParserUtil.parseCommand(cmd));
                } catch (CommandException ex) {
                    // 继续监听
                    OutputUtil.log("%s\n",ex.getErrorCode().getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
