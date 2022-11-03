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

    public void init() {
        OutputUtil.print("开始初始化：%s\n", path);
        this.root = ParserUtil.parseBmk(path);
        OutputUtil.println("初始化完成");
    }

    public void open(String path) {
        if (Boolean.TRUE.equals(StringUtil.isEmpty(path)) || Boolean.FALSE.equals(StringUtil.isBmkFile(path))) {
            this.path = Constants.RESOURCE_PATH + "data.bmk";
            OutputUtil.print("未指定路径或指定非 bmk 文件，默认存储至：%s\n", this.path);
        } else {
            this.path = path;
        }
        init();
    }

    public void lsTree() {
        if (this.root != null) {
            StringBuilder out = new StringBuilder();
            this.root.walk(new PrinterTreeVisitor<>(out));
            OutputUtil.println(out.toString());
        }
    }

    public void save(String path) {
        this.path = path;
        save();
    }

    public void save() {
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
    }

    public void addDirectoryAt(Title title, String at) {
        if (this.root == null) {
            title.setLevel(1);
            this.root = title;
        } else {
            at = StringUtil.removeQuotationMarks(at);
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
    }

    public void addItemAt(Link link, String at) {
        if (this.root == null) {
            this.root = link;
        } else {
            at = StringUtil.removeQuotationMarks(at);
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
    }


    public void deleteDirectory(String name) {
        this.root.removeNameNodes(StringUtil.removeQuotationMarks(name), Title.class);
    }

    public void deleteItem(String name) {
        this.root.removeNameNodes(StringUtil.removeQuotationMarks(name), Link.class);
    }

    public void accessItem(String[] args) {
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
    }

    public void accessDirectory(String[] args) {
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
    }
}
