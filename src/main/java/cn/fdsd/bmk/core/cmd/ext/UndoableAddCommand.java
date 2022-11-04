package cn.fdsd.bmk.core.cmd.ext;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.core.cmd.AddCommand;

import java.util.List;
import java.util.stream.Collectors;

public class UndoableAddCommand extends UndoableCommand {

    public UndoableAddCommand(AddCommand command) {
        super(command);
    }

    @Override
    public int command() {
        if (getCommand() instanceof AddCommand) {
            List<Node> nodeList = ((AddCommand) getCommand()).getNodes();
            if (nodeList.isEmpty()) {
                this.getCommand().execute();
            } else {
                for (int i = nodeList.size() - 1; i >= 0; i--) {
                    if (nodeList.get(i) instanceof Title) {
                        bookmark.addDirectoryAt((Title) nodeList.get(i),
                                nodeList.get(i).getParent() == null ? null : nodeList.get(i).getParent().getName());
                    } else if (nodeList.get(i) instanceof Link) {
                        bookmark.addItemAt((Link) nodeList.get(i),
                                nodeList.get(i).getParent() == null ? null : nodeList.get(i).getParent().getName());
                    }
                }
            }
            return 1;
        }
        return -1;
    }

    @Override
    protected int restore() {
        if (getCommand() instanceof AddCommand) {
            List<Node> nodeList = ((AddCommand) getCommand()).getNodes();
            return bookmark.deleteNodesByUuid(nodeList.stream().map(Node::getUuid).collect(Collectors.toList()));
        }
        return -1;
    }
}
