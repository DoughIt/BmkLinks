package cn.fdsd.bmk.core.cmd;

import cn.fdsd.bmk.domain.enums.CommandEnum;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
public class SaveCommand extends GeneralCommand {
    @Override
    public int execute() {
        if (po.getName().equals(CommandEnum.SAVE)) {
            return bookmark.save();
        }
        return -1;
    }
}