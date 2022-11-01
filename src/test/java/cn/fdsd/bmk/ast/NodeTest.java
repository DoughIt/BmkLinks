package cn.fdsd.bmk.ast;


import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.ast.visitor.PrinterTreeVisitor;
import org.junit.jupiter.api.Test;

class NodeTest {
    @Test
    void walk() {
        Title node = Title.builder().text("h1").level(1).build();
        node.appendChild(Link.builder().text("ehall").url("http://ehall.fudan.edu.cn").build());
        node.appendChild(Link.builder().text("elearning").url("http://elearning.fudan.edu.cn").build());
        node.accept(new PrinterTreeVisitor<>());
    }
}