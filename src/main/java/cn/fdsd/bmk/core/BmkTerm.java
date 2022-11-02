package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.domain.po.CommandPo;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.HintScanner;
import cn.fdsd.bmk.utils.ParserUtil;
import cn.fdsd.bmk.utils.StringUtil;

import java.io.IOException;

public class BmkTerm {
    private static final String HINT = "\uD83D\uDD16";

    public static void main(String[] args) {
        outputHelp();
        Bookmark bookmark = new Bookmark("./data.bmk");
        if (args.length > 0) {
            bookmark.setPath(args[0]);
            bookmark.init();
        }
        // 监听输入、处理命令、打印输出
        CommandInvoker invoker = CommandFactory.createInvoker();
        try (HintScanner scanner = new HintScanner(System.in)) {
            String cmd;
            while ((cmd = scanner.nextLine(HINT, false)) != null) {
//                invoker.execute(bookmark, ParserUtil.parseCommand(cmd));
                invoker.execute(bookmark, CommandPo.builder().name(CommandEnum.LS_TREE).build());
            }
        } catch (CommandException ex) {
            System.out.printf("%s", ex.getErrorCode().getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void outputHelp() {
        System.out.println(StringUtil.repeatStr("- ", 32));
        System.out.printf("在提示符（%s）后输入命令，按回车键执行命令！", HINT);
        StringBuilder commandHelper = new StringBuilder();
        CommandEnum.exportHelp(commandHelper);
        System.out.println(commandHelper);
        System.out.println(StringUtil.repeatStr("- ", 32));
    }
}
