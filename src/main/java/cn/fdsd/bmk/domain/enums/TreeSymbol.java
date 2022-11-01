package cn.fdsd.bmk.domain.enums;

/**
 * 目录结构符号
 *
 * @author Jerry Zhang
 * create: 2022-11-01 18:48
 */
public enum TreeSymbol {
    FIRST("├──"),
    LAST("└──"),
    SPAN("│  "),
    EMPTY("   ");
    private final String symbol;

    public String getSymbol() {
        return this.symbol;
    }
    TreeSymbol(String symbol) {
        this.symbol = symbol;
    }
}
