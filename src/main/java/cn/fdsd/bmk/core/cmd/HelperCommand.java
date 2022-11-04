package cn.fdsd.bmk.core.cmd;


import cn.fdsd.bmk.domain.cont.Constants;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.utils.OutputUtil;
import cn.fdsd.bmk.utils.StringUtil;

/**
 * @author Jerry Zhang
 * create: 2022-11-04 11:53
 */
public class HelperCommand extends GeneralCommand {
    @Override
    public int execute() {
        OutputUtil.println(StringUtil.repeatStr("- ", 32));
        OutputUtil.print("在提示符（%s）后输入命令，按回车键执行命令！", Constants.HINT);
        StringBuilder commandHelper = new StringBuilder();
        CommandEnum.exportHelp(commandHelper);
        OutputUtil.println(commandHelper.toString());
        OutputUtil.println(StringUtil.repeatStr("- ", 32));
        return 1;
    }
}