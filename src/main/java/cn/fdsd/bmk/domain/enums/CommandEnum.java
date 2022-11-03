package cn.fdsd.bmk.domain.enums;


/**
 * @author Jerry Zhang
 * create: 2022-11-02 12:42
 */
public enum CommandEnum {
    ADD("add", null),
    ADD_TITLE("add-title", ADD, "新增目录", new String[]{"add-title '课程'", "add-title '软件开发' at '课程'"}, true),
    ADD_BOOKMARK("add-bookmark", ADD, "新增书签", new String[]{"add-bookmark 'elearning'@'https://elearning.fudan.edu.c' at '课程'"}, true),

    DELETE("delete", null),
    DEL_TITLE("delete-title", DELETE, "删除目录", new String[]{"delete-title '课程'"}, true),
    DEL_BOOKMARK("delete-bookmark", DELETE, "删除书签", new String[]{"delete-bookmark 'elearning'"}, true),
    OPEN("open", null, "从文件打开书签栏", new String[]{"open './data.bmk'"}, true),     // 从文件打开书签
    BOOKMARK("bookmark", OPEN),    // 启动程序
    EDIT("edit", OPEN, "编辑书签栏", new String[]{"edit './data.bmk'"}, true),     // 编辑指定书签
    READ("read", OPEN, "访问书签", new String[]{"read 'elearning'"}, true),     // 访问指定书签
    READ_TITLE("read-title", OPEN, "访问文件夹，选中该文件夹", new String[]{"read-title '课程'"}, true),     // 访问指定书签

    SAVE("save", null, "保存书签栏", new String[]{"save ['./data.bmk']"}, true),     // 持久化存储到 bmk 文件
    UNDO("undo", null, "撤回", null, true),     // undo 功能
    REDO("redo", null, "重做", null, true),     // redo 功能
    SHOW("show", null),
    SHOW_TREE("show-tree", SHOW, "可视化当前编辑内容", null, true),   // 可视化当前编辑内容
    LS_TREE("ls-tree", SHOW, "可视化当前选中的文件夹目录结构", null, true),   // 可视化当前目录结构
    HISTORY("history", SHOW, "操作历史", null, true),   // 可视化当前目录结构
    EXIT("exit", null, "退出", null, true);   // 退出

    private final String name;
    private final CommandEnum parent;

    private final String desc;
    private final String[] examples;

    private final Boolean available;

    public String getName() {
        return this.name;
    }

    public CommandEnum getParent() {
        return this.parent;
    }

    public String getDesc() {
        return this.desc;
    }

    public String[] getExamples() {
        return this.examples;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    CommandEnum(String name, CommandEnum parent) {
        this(name, parent, "", null, false);
    }

    CommandEnum(String name, CommandEnum parent, String desc, String[] examples, Boolean available) {
        this.name = name;
        this.parent = parent;
        this.desc = desc;
        this.examples = examples;
        this.available = available;
    }

    public static CommandEnum parseToEnum(String name) {
        for (CommandEnum commandEnum : values()) {
            if (commandEnum.getName().equals(name)) {
                return commandEnum;
            }
        }
        return null;
    }

    public static void exportHelp(StringBuilder out) {
        int count = 1;
        for (CommandEnum commandEnum : values()) {
            if (Boolean.TRUE.equals(commandEnum.getAvailable())) {
                out.append("\n");
                out.append(count++).append(".\t").append(commandEnum.getName()).append("\t").append(commandEnum.getDesc());
                if (commandEnum.getExamples() != null) {
                    for (int i = 0; i < commandEnum.getExamples().length; i++) {
                        out.append("\n\t").append(commandEnum.getExamples()[i]);
                    }
                }
            }
        }
    }
}
