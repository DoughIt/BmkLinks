package cn.fdsd.bmk.core.cmd.ext;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.core.cmd.AddCommand;
import cn.fdsd.bmk.core.cmd.DelCommand;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Jerry Zhang
 * create: 2022-11-02 13:02
 */
public class UndoableDelCommand extends UndoableCommand {
    public UndoableDelCommand(DelCommand command) {
        super(command);
    }


    @Override
    public int command() {
        if (getCommand() instanceof DelCommand) {
            List<Node> nodeList = ((DelCommand) getCommand()).getNodes();
            if (nodeList.isEmpty()) {
                return this.getCommand().execute();
            } else {
                return bookmark.deleteNodesByUuid(nodeList.stream().map(Node::getUuid).collect(Collectors.toList()));
            }
        }
        return -1;
    }

    @Override
    protected int restore() {
        if (getCommand() instanceof DelCommand) {
            List<Node> nodeList = ((DelCommand) getCommand()).getNodes();
            for (int i = nodeList.size() - 1; i >= 0; i--) {
                if (nodeList.get(i) instanceof Title) {
                    bookmark.addDirectoryAt((Title) nodeList.get(i), nodeList.get(i).getParent().getName());
                } else if (nodeList.get(i) instanceof Link) {
                    bookmark.addItemAt((Link) nodeList.get(i), nodeList.get(i).getParent().getName());
                }
            }
            return 1;
        }
        return -1;
    }
}
