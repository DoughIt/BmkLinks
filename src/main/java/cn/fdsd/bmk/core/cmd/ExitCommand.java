package cn.fdsd.bmk.core.cmd;


/**
 *
 * @author Jerry Zhang
 * create: 2022-11-03 17:34
 */
public class ExitCommand extends GeneralCommand {
    @Override
    public int execute() {
        // todo 提醒保存
        System.exit(1);
        return 1;
    }
}