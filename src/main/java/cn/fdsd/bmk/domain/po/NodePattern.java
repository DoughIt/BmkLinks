package cn.fdsd.bmk.domain.po;

import cn.fdsd.bmk.domain.enums.NodeTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单例模式实现
 *
 * @author Jerry Zhang
 * create: 2022-11-02 14:27
 */
public class NodePattern {
    private final Map<NodeTypes, Pattern> patternMap = new HashMap<>(8);

    private static class PatternHolder {
        private static final NodePattern INSTANCE = new NodePattern();
    }

    private NodePattern() {
        this.initPatterns();
    }

    private void initPatterns() {
        patternMap.put(NodeTypes.TITLE, Pattern.compile("^(#+)(.*)"));
        patternMap.put(NodeTypes.LINK, Pattern.compile("\\[([\\s\\S]*?)]\\(([\\s\\S]*?)\\)"));
    }

    public static NodePattern getInstance() {
        return PatternHolder.INSTANCE;
    }

    public boolean matches(NodeTypes type, String text) {
        return patternMap.get(type).matcher(text).matches();
    }

    public Matcher matcher(NodeTypes type, String text) {
        return patternMap.get(type).matcher(text);
    }
}
