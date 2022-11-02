package cn.fdsd.bmk.domain.po;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.OutputMdVisitor;
import cn.fdsd.bmk.ast.visitor.PrinterTreeVisitor;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.ParserUtil;
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
        System.out.println("开始导入……");
        this.root = ParserUtil.parseBmk(path);
    }

    public void lsTree() {
        if (this.root != null) {
            this.root.walk(new PrinterTreeVisitor<>());
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
        } catch (FileNotFoundException e) {
            throw new CommandException(CommandErrorCode.SAVE_FAILED);
        }
    }
}
