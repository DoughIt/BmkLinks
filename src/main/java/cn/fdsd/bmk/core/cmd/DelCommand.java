package cn.fdsd.bmk.core.cmd;


import cn.fdsd.bmk.ast.Node;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
@EqualsAndHashCode(callSuper = false)
public class DelCommand extends GeneralCommand {
    @Getter
    private List<Node> nodes = new ArrayList<>();

    @Override
    public int execute() {
        switch (po.getName()) {
            case DEL_TITLE:
                return bookmark.deleteDirectoryAt(po.getArgs()[0], nodes);
            case DEL_BOOKMARK:
                return bookmark.deleteItemAt(po.getArgs()[0], nodes);
            default:
                return -1;
        }
    }
}
