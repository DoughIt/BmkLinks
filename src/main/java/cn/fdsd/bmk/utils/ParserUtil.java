package cn.fdsd.bmk.utils;

import cn.fdsd.bmk.ast.Node;
import cn.fdsd.bmk.ast.node.Link;
import cn.fdsd.bmk.ast.node.Title;
import cn.fdsd.bmk.domain.enums.CommandEnum;
import cn.fdsd.bmk.domain.enums.NodeTypes;
import cn.fdsd.bmk.domain.po.CommandPo;
import cn.fdsd.bmk.domain.po.NodePattern;
import cn.fdsd.bmk.exception.CommandErrorCode;
import cn.fdsd.bmk.exception.CommandException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Jerry Zhang
 * create: 2022-11-01 20:08
 */
public class ParserUtil {
    public static Node parseBmk(String bmkFile) {
        if (!Boolean.TRUE.equals(StringUtil.isBmkFile(bmkFile))) {
            throw new CommandException(CommandErrorCode.NOT_BMK);
        }
        Node root = null;
        try (BufferedReader br = new BufferedReader(new FileReader(bmkFile));) {
            String line;
            Node parent = null;
            while ((line = br.readLine()) != null) {
                Node node = null;
                if (isTitle(line)) {
                    Matcher matcher = NodePattern.getInstance().matcher(NodeTypes.TITLE, line);
                    if (matcher.find()) {
                        node = Title.builder().level(matcher.group(1).length()).text(matcher.group(2)).build();
                    }
                } else if (isLink(line)) {
                    Matcher matcher = NodePattern.getInstance().matcher(NodeTypes.LINK, line);
                    if (matcher.find())
                        node = Link.builder().text(matcher.group(1)).url(matcher.group(2)).build();
                }
                if (node != null) {
                    node.setFullContent(line);
                    if (node instanceof Title) {
                        while (parent != null && ((Title) parent).getLevel() > ((Title) node).getLevel()) {
                            parent = parent.getParent();
                        }
                        if (parent == null) {
                            root = node;
                        } else if (((Title) parent).getLevel().equals(((Title) node).getLevel())) {
                            parent.insertAfter(node);
                        } else {
                            ((Title) node).setLevel(((Title) parent).getLevel() + 1);
                            parent.appendChild(node);
                        }
                        parent = node;
                    } else if (parent != null) {
                        parent.appendChild(node);
                    }
                }
            }
        } catch (IOException e) {
            throw new CommandException(CommandErrorCode.PARSE_FAILED);
        }
        return root;
    }


    private static boolean isTitle(String line) {
        return !StringUtil.isEmpty(line) && NodePattern.getInstance().matches(NodeTypes.TITLE, line);
    }

    private static boolean isLink(String line) {
        return !StringUtil.isEmpty(line) && NodePattern.getInstance().matches(NodeTypes.LINK, line);
    }

    public static CommandPo parseCommand(String line) {
        if (Boolean.TRUE.equals(StringUtil.isEmpty(line))) {
            throw new CommandException(CommandErrorCode.PARSE_FAILED);
        }
        Pattern atPattern = Pattern.compile("\\s*(at)\\s*");
        Matcher atMatcher = atPattern.matcher(line);
        String leftCmd = line;
        String atOption = null;
        if (atMatcher.find()) {
            leftCmd = line.substring(0, line.indexOf(" at "));
            atOption = line.substring(line.indexOf(" at ") + 4).trim();
        }
        Pattern pattern = Pattern.compile("(\\S*)(\\s+(\\S*))?");
        Matcher matcher = pattern.matcher(leftCmd);
        if (matcher.find()) {
            CommandEnum commandEnum = CommandEnum.parseToEnum(matcher.group(1));
            return CommandPo.builder()
                    .name(commandEnum)
                    .args(matcher.groupCount() >= 3 ? new String[]{matcher.group(3)} : null)
                    .atOption(atOption).build();
        }
        return null;
    }
}
