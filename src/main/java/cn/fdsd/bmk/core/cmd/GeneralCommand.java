package cn.fdsd.bmk.core.cmd;

import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.domain.po.CommandPo;
import lombok.Data;

/**
 * @author Jerry Zhang
 * create: 2022-11-02 12:55
 */
@Data
public abstract class GeneralCommand implements Command {
    protected Bookmark bookmark;
    protected CommandPo po;
}
