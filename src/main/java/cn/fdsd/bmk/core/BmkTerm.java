package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;
import cn.fdsd.bmk.domain.cont.Constants;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.HintScanner;
import cn.fdsd.bmk.utils.ParserUtil;
import cn.fdsd.bmk.utils.StringUtil;

import java.io.IOException;


public class BmkTerm {

    public static void main(String[] args) {
        outputHelp();
        Bookmark bookmark = new Bookmark(Constants.RESOURCE_PATH + "data.bmk");
        if (args.length > 0) {
            bookmark.setPath(args[0]);
            bookmark.init();
        }
        // 监听输入、处理命令、打印输出
        CommandInvoker invoker = CommandFactory.createInvoker();
        try (HintScanner scanner = new HintScanner(System.in)) {
            String cmd;
            while ((cmd = scanner.nextLine(Constants.HINT, false)) != null) {
                try {
                    invoker.execute(bookmark, ParserUtil.parseCommand(cmd));
                } catch (CommandException ex) {
                    // 继续监听
                    System.out.printf("%s\n", ex.getErrorCode().getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void outputHelp() {
        System.out.println(StringUtil.repeatStr("- ", 32));
        System.out.printf("在提示符（%s）后输入命令，按回车键执行命令！", Constants.HINT);
        StringBuilder commandHelper = new StringBuilder();
        CommandEnum.exportHelp(commandHelper);
        System.out.println(commandHelper);
        System.out.println(StringUtil.repeatStr("- ", 32));
    }
}
