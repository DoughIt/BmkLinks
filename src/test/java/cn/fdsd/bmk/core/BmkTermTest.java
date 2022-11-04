package cn.fdsd.bmk.core;

import cn.fdsd.bmk.core.cmd.factory.CommandFactory;
import cn.fdsd.bmk.core.cmd.factory.CommandInvoker;
import cn.fdsd.bmk.domain.po.Bookmark;
import cn.fdsd.bmk.exception.CommandException;
import cn.fdsd.bmk.utils.OutputUtil;
import cn.fdsd.bmk.utils.ParserUtil;
import org.junit.jupiter.api.*;


@Nested
@DisplayName("组合测试所有功能")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BmkTermTest {
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    private static final String SRC_FILE = RESOURCE_PATH + "test.bmk";
    private static final String TEST_FILE = RESOURCE_PATH + "test1.bmk";
    private static final String LS_TREE = "ls-tree";
    private static final String SHOW_TREE = "show-tree";
    private static final String UNDO = "undo";
    private static final String REDO = "redo";
    private static final String SAVE = "save";
    private static final String HISTORY = "history";
    private static final String HELP = "help";

    private static final String HINT = "> ";
    private CommandInvoker invoker;
    private Bookmark bookmark;

    @BeforeAll
    void init() {
        invoker = CommandFactory.createInvoker();
        bookmark = new Bookmark("test2.bmk");
    }

    @Test
    @DisplayName("测试 open / edit 功能")
    void testOpen() {
        String openCmd = "open test3.bmk";
        // 打开 test3.bmk 文件
        invoker.execute(bookmark, ParserUtil.parseCommand(openCmd));
        Assertions.assertEquals("test3.bmk", bookmark.getPath());

        String editCmd = "edit " + TEST_FILE;
        // 打开 test1.bmk 文件
        invoker.execute(bookmark, ParserUtil.parseCommand(editCmd));
        Assertions.assertEquals(TEST_FILE, bookmark.getPath());
    }

    @Test
    @DisplayName("测试 add-title / add-bookmark 功能")
    void testAdd() {
        String addTitle1 = "add-title 课程";
        println(addTitle1);
        invoker.execute(bookmark, ParserUtil.parseCommand(addTitle1));
        String addTitle2 = "add-title 软件开发 at 课程";
        println(addTitle2);
        invoker.execute(bookmark, ParserUtil.parseCommand(addTitle2));
        String addBookmark1 = "add-bookmark elearning@elearning.fudan.edu.cn at 课程";
        println(addBookmark1);
        invoker.execute(bookmark, ParserUtil.parseCommand(addBookmark1));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
    }

    @Test
    @DisplayName("测试 delete-title / delete-bookmark 功能")
    void testDel() {
        // 先新增数据
        testAdd();

        String deleteBookmark = "delete-bookmark elearning";
        println(deleteBookmark);
        invoker.execute(bookmark, ParserUtil.parseCommand(deleteBookmark));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
        String deleteTitle = "delete-title 课程";
        println(deleteTitle);
        invoker.execute(bookmark, ParserUtil.parseCommand(deleteTitle));
        // 删除根节点，书签栏为空
        println(bookmark.isEmpty() ? "书签栏为空" : "书签栏不为空");
        Assertions.assertTrue(bookmark.isEmpty());

        // 再新增
        testAdd();
        String deleteTitle1 = "delete-title 软件开发";
        println(deleteTitle1);
        invoker.execute(bookmark, ParserUtil.parseCommand(deleteTitle1));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
    }

    @Test
    @DisplayName("测试 undo / redo 功能")
    void testUndoRedo() {
        // history、ls-tree、show-tree、help 等查看功能可以被跳过
        // 先进行增删操作
        testDel();
        println(HISTORY);
        invoker.execute(bookmark, ParserUtil.parseCommand(HISTORY));
        println(UNDO);
        invoker.execute(bookmark, ParserUtil.parseCommand(UNDO));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
        println(UNDO);
        invoker.execute(bookmark, ParserUtil.parseCommand(UNDO));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
        println(REDO);
        invoker.execute(bookmark, ParserUtil.parseCommand(REDO));
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
        println(HISTORY);
        invoker.execute(bookmark, ParserUtil.parseCommand(HISTORY));
    }

    @Test
    @DisplayName("测试不允许 undo / redo 的情况")
    void testCannotUndoRedo() {
        // history、ls-tree、show-tree、help 等查看功能可以被跳过
        // save / open / edit 等操作不允许 undo / redo
        // 先进行增删操作
        testDel();
        println(HISTORY);
        invoker.execute(bookmark, ParserUtil.parseCommand(HISTORY));
        println(SAVE);
        invoker.execute(bookmark, ParserUtil.parseCommand(SAVE));
        try {
            println(UNDO);
            invoker.execute(bookmark, ParserUtil.parseCommand(UNDO));
        } catch (CommandException e) {
            OutputUtil.println(e.getErrorCode().getMessage());
        }
        String addBookmark1 = "add-bookmark ehall@ehall.fudan.edu.cn at 课程";
        println(addBookmark1);
        println(HISTORY);
        invoker.execute(bookmark, ParserUtil.parseCommand(HISTORY));
        invoker.execute(bookmark, ParserUtil.parseCommand(addBookmark1));
        // 没有 undo 不能直接 redo
        try {
            println(REDO);
            invoker.execute(bookmark, ParserUtil.parseCommand(REDO));
        } catch (CommandException e) {
            OutputUtil.println(e.getErrorCode().getMessage());
        }
        println(HISTORY);
    }

    @Test
    @DisplayName("测试其他基础功能功能")
    void testGeneralCommands() {
        // 先进行增删操作
        testDel();
        // 访问书签
        String readBookmark = "read-bookmark elearning";
        println(readBookmark);
        println(readBookmark);
        invoker.execute(bookmark, ParserUtil.parseCommand(readBookmark));
        invoker.execute(bookmark, ParserUtil.parseCommand(readBookmark));
        // 可视化
        println(LS_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(LS_TREE));
        println(SHOW_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(SHOW_TREE));
        // 打开新的书签栏
        String open = "open " + SRC_FILE;
        println(open);
        invoker.execute(bookmark, ParserUtil.parseCommand(open));
        // 可视化
        println(SHOW_TREE);
        invoker.execute(bookmark, ParserUtil.parseCommand(SHOW_TREE));
        // 展示命令执行历史（成功执行的命令），已经被 undo 命令不会计入
        println(HISTORY);
        invoker.execute(bookmark, ParserUtil.parseCommand(HISTORY));
        // 打印帮助文档
        println(HELP);
        invoker.execute(bookmark, ParserUtil.parseCommand(HELP));
    }

    private void println(String text) {
        OutputUtil.println(HINT + text);
    }
}