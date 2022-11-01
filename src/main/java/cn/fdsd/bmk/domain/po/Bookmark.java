package cn.fdsd.bmk.domain.po;

import cn.fdsd.bmk.ast.Node;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:09
 */
@Builder
@EqualsAndHashCode
public class Bookmark {
    private String path;    // 本地存储路径
    private Node node;      // 书签对象树

}
