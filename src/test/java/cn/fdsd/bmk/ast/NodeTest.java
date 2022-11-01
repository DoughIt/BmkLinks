package cn.fdsd.bmk.ast;


import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.ast.visitor.PrinterTreeVisitor;
import org.junit.jupiter.api.Test;

class NodeTest {
    @Test
    void walk() {
        Title title = Title.builder().text("复旦").level(1).build();

        Title title1 = Title.builder().text("复旦官网").level(2).build();
        title1.appendChild(Link.builder().text("官网链接").url("http://fudan.edu.cn").build());
        title.appendChild(title1);

        Title title2 = Title.builder().text("复旦系统").level(2).build();
        title2.appendChild(Link.builder().text("ehall").url("http://ehall.fudan.edu.cn").build());
        title2.appendChild(Link.builder().text("elearning").url("http://elearning.fudan.edu.cn").build());
        title.appendChild(title2);

        title.accept(new PrinterTreeVisitor<>());

    }
}