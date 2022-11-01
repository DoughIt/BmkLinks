package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;

public class BmkTerm {
    public static void main(String[] args) {
        // todo 监听输入、处理命令、打印输出
        CommandInvoker invoker = CommandFactory.createInvoker();

    }
}
