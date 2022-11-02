package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.ParserUtil;

import java.util.Scanner;

public class BmkTerm {
    public static void main(String[] args) {
        Bookmark bookmark = new Bookmark("./data.bmk");
        if (args.length > 0) {
            bookmark.setPath(args[0]);
            bookmark.init();
        }
        // 监听输入、处理命令、打印输出
        CommandInvoker invoker = CommandFactory.createInvoker();
        try (Scanner scanner = new Scanner(System.in)) {
            String cmd;
            while ((cmd = scanner.nextLine()) != null) {
                invoker.execute(bookmark, ParserUtil.parseCommand(cmd));
            }
        } catch (CommandException ex) {
            System.out.println(ex.getErrorCode().getMessage());
        }
    }
}
