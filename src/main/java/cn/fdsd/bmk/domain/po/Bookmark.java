package cn.fdsd.bmk.domain.po;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.ast.visitor.OutputMdVisitor;
import cn.fdsd.bmk.ast.visitor.PrinterTreeVisitor;
import cn.fdsd.bmk.domain.cont.Constants;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.OutputUtil;
import cn.fdsd.bmk.utils.ParserUtil;
import cn.fdsd.bmk.utils.StringUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:09
 */
@EqualsAndHashCode
public class Bookmark {
    @Getter
    @Setter
    private String path;    // 本地存储路径
    private Node root;      // 书签对象树

    public Bookmark(String path) {
        this.path = path;
        this.root = null;
    }

    public int init() {
        OutputUtil.print("开始初始化：%s\n", path);
        this.root = ParserUtil.parseBmk(path);
        OutputUtil.println("初始化完成");
        return 1;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int open(String path) {
        if (Boolean.FALSE.equals(StringUtil.isEmpty(this.path)) && this.root != null) {
            OutputUtil.print("工作区存在内容，自动保存至：%s\n", this.path);
            save();
            OutputUtil.println("已保存，即将打开新文件");
        }
        if (Boolean.TRUE.equals(StringUtil.isEmpty(path)) || Boolean.FALSE.equals(StringUtil.isBmkFile(path))) {
            this.path = Constants.RESOURCE_PATH + "data.bmk";
            OutputUtil.print("未指定路径或指定非 bmk 文件，默认存储至：%s\n", this.path);
        } else {
            this.path = path;
        }
        return init();
    }

    public int showTree() {
        if (this.root != null) {
            StringBuilder out = new StringBuilder();
            this.root.walk(new OutputMdVisitor<>(out));
            OutputUtil.println(out.toString());
            return 1;
        }
        throw new CommandException(CommandErrorCode.EMPTY);
    }

    public int lsTree() {
        if (this.root != null) {
            StringBuilder out = new StringBuilder();
            this.root.walk(new PrinterTreeVisitor<>(out));
            OutputUtil.println(out.toString());
            return 1;
        }
        throw new CommandException(CommandErrorCode.EMPTY);
    }

    public int save(String path) {
        this.path = path;
        return save();
    }

    public int save() {
        if (this.root == null) {
            throw new CommandException(CommandErrorCode.EMPTY);
        }
        StringBuilder out = new StringBuilder();
        this.root.walk(new OutputMdVisitor<>(out));
        try (PrintWriter pw = new PrintWriter(this.path)) {
            pw.write(out.toString());
            pw.flush();
        } catch (FileNotFoundException e) {
            throw new CommandException(CommandErrorCode.SAVE_FAILED);
        }
        return 1;
    }

    public int addDirectoryAt(Title title, String at) {
        if (this.root == null) {
            title.setLevel(1);
            this.root = title;
        } else {
            if (!Boolean.TRUE.equals(StringUtil.isEmpty(at))) {
                // 放在 at 级目录下
                Node parent = this.root;
                while (parent != null) {
                    parent.appendChildInNameNode(at, Title.class, title);
                    parent = parent.getNext();
                }
            } else {
                // title 默认放在 root 目录同级
                this.root.insertAfter(title);
            }
        }
        return 1;
    }

    public int addItemAt(Link link, String at) {
        if (this.root == null) {
            this.root = link;
        } else {
            if (!Boolean.TRUE.equals(StringUtil.isEmpty(at))) {
                // 放在 at 级目录下
                Node parent = this.root;
                while (parent != null) {
                    parent.appendChildInNameNode(at, Title.class, link);
                    parent = parent.getNext();
                }
            } else {
                // link 默认放在 root 目录下
                this.root.appendChild(link);
            }
        }
        return 1;
    }

    public int deleteNodesByUuid(List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return -1;
        }
        Node parent = this.root;
        while (parent != null) {
            parent.removeNodesByUuids(uuids);
            parent = parent.getNext();
        }
        if (this.root != null && uuids.contains(this.root.getUuid())) {
            this.root = this.root.getNext();
        }
        return 1;
    }

    public int deleteDirectoryAt(String name, List<Node> deleteDirectories) {
        if (Boolean.TRUE.equals(StringUtil.isEmpty(name))) {
            throw new CommandException(CommandErrorCode.LOST);
        }
        Node parent = this.root;
        while (parent != null) {
            parent.removeNameNodes(name, Title.class, deleteDirectories);
            for (Node node : deleteDirectories) {
                if (node.getUuid().equals(this.root.getUuid())) {
                    this.root = parent.getNext();
                    break;
                }
            }
            parent = parent.getNext();
        }

        return 1;
    }

    public int deleteItemAt(String name, List<Node> deleteItems) {
        if (Boolean.TRUE.equals(StringUtil.isEmpty(name))) {
            throw new CommandException(CommandErrorCode.LOST);
        }
        Node parent = this.root;
        while (parent != null) {
            parent.removeNameNodes(name, Link.class, deleteItems);
            parent = parent.getNext();
        }
        return 1;
    }

    public int accessItem(String[] args) {
        if (args == null) {
            throw new CommandException(CommandErrorCode.LOST);
        }
        for (String arg : args) {
            Node parent = this.root;
            while (parent != null) {
                parent.updateStatus(arg, true, Link.class);
                parent = parent.getNext();
            }
        }
        return 1;
    }

    public int accessDirectory(String[] args) {
        if (args == null) {
            throw new CommandException(CommandErrorCode.LOST);
        }
        for (String arg : args) {
            Node parent = this.root;
            while (parent != null) {
                parent.updateStatus(arg, true, Title.class);
                parent = parent.getNext();
            }
        }
        return 1;
    }
}
