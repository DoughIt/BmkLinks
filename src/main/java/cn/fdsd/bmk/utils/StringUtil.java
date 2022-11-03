package cn.fdsd.bmk.utils;

import java.util.Collections;

/**
 * 字符串处理工具
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:28
 */
public class StringUtil {
    public static String repeatStr(String seed, int n) {
        return String.join("", Collections.nCopies(n, seed));
    }

    public static Boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static Boolean isBmkFile(String filePath) {
        return !isEmpty(filePath) && filePath.endsWith(".bmk");
    }

    public static String removeQuotationMarks(String text) {
        if (Boolean.TRUE.equals(isEmpty(text))) {
            return "";
        }
        return text.replace("\"", "");
    }
}
