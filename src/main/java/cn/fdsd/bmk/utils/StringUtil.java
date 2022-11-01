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
}
