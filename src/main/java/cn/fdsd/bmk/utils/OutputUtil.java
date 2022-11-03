package cn.fdsd.bmk.utils;

/**
 *
 * @author Jerry Zhang
 * create: 2022-11-03 13:41
 */
public class OutputUtil {
    public static void log(String format, Object... args) {
        print(format, args);
    }

    public static void print(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static void println(String text) {
        System.out.println(text);
    }
}
