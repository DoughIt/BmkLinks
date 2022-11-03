package cn.fdsd.bmk.utils;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.visitor.PrinterTreeVisitor;
import cn.fdsd.bmk.domain.po.CommandPo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserUtilTest {
    private static final String RESOURCE_PATH = System.getProperty("user.dir") + "/src/test/resources/";

    @Test
    void testParseBmk() {
        String testFile = RESOURCE_PATH + "test.bmk";
        String expected = "└──复旦\n" +
                "   ├──复旦官网\n" +
                "   │  └──官网链接 http://fudan.edu.cn (已访问 0 次)\n" +
                "   └──复旦系统\n" +
                "      ├──ehall http://ehall.fudan.edu.cn (已访问 0 次)\n" +
                "      └──elearning http://elearning.fudan.edu.cn (已访问 0 次)";
        Node root = ParserUtil.parseBmk(testFile);

        assertNotNull(root);

        StringBuilder out = new StringBuilder();
        root.walk(new PrinterTreeVisitor<>(out));

        assertEquals(expected, out.toString().trim());
    }

    @Test
    void testParseSimpleCommand() {
        String lsTree = "ls-tree";
        CommandPo lsTreePo = ParserUtil.parseCommand(lsTree);

        assertNotNull(lsTreePo);

        assertEquals(lsTree, lsTreePo.getName().getName());
    }

    @Test
    void testParseComplexCommand() {
        String addBookmark = "add-bookmark 'ehall'@'https://ehall.fudan.edu.cn' at '复旦系统'";
        CommandPo addBookmarkPo = ParserUtil.parseCommand(addBookmark);

        assertNotNull(addBookmarkPo);

        assertEquals("add-bookmark", addBookmarkPo.getName().getName());
        assertNotNull(addBookmarkPo.getArgs());
        assertNotEquals(0, addBookmarkPo.getArgs().length);
        assertEquals("'ehall'@'https://ehall.fudan.edu.cn'", addBookmarkPo.getArgs()[0]);
        assertNotNull(addBookmarkPo.getAtOption());
        assertEquals("'复旦系统'", addBookmarkPo.getAtOption());
    }
}