package cn.fdsd.bmk.domain.enums;

import lombok.Data;

/**
 *
 * @author Jerry Zhang
 * create: 2022-11-02 12:42
 */
public enum CommandEnum {
    ADD("add", null),
    ADD_TITLE("add-title", ADD),
    ADD_BOOKMARK("add-bookmark", ADD),

    DELETE("delete", null),
    DEL_TITLE("delete-title", DELETE),
    DEL_BOOKMARK("delete-bookmark", DELETE),
    OPEN("open", null),     // 从文件打开书签
    BOOKMARK("bookmark", OPEN),    // 启动程序
    EDIT("edit", OPEN),     // 编辑指定书签
    READ("read", OPEN),     // 访问指定书签

    SAVE("save", null),     // 持久化存储到 bmk 文件
    UNDO("undo", null),     // undo 功能
    REDO("redo", null),     // redo 功能
    SHOW("show", null),
    SHOW_TREE("show-tree", SHOW),   // 可视化当前编辑内容
    LS_TREE("ls-tree", SHOW);   // 可视化当前目录结构

    private String name;
    private CommandEnum parent;

    public String getName(){
        return this.name;
    }

    public CommandEnum getParent() {
        return this.parent;
    }

    CommandEnum(String name, CommandEnum parent){
        this.name = name;
        this.parent = parent;
    }

    public static CommandEnum parseToEnum(String name) {
        for (CommandEnum commandEnum: values()) {
            if (commandEnum.getName().equals(name)) {
                return commandEnum;
            }
        }
        return null;
    }
}
