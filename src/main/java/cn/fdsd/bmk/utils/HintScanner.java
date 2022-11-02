package cn.fdsd.bmk.utils;


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 扩展输入：输入时提示
 *
 * @author Jerry Zhang
 * create: 2022-11-02 21:49
 */
public class HintScanner implements Iterator<String>, Closeable {
    private Scanner scanner;

    public HintScanner(InputStream in) {
        this.scanner = new Scanner(in);
    }

    public String nextLine(String hint, boolean comma) {
        System.out.printf("%s %s", hint, comma ? ": " : " ");
        return this.scanner.nextLine();
    }

    @Override
    public void close() throws IOException {
        this.scanner.close();
    }

    @Override
    public boolean hasNext() {
        return this.scanner.hasNext();
    }

    @Override
    public String next() {
        return this.scanner.next();
    }
}
