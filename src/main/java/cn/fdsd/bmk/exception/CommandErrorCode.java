package cn.fdsd.bmk.exception;

/**
 * 定义各类返回码的值
 *
 * @author Jerry Zhang
 * create: 2022-11-02 11:00
 */
public enum CommandErrorCode implements ErrorCode {
    NOT_BMK(1000, "不支持读取非 bmk 格式的文件"),
    EMPTY(1001, "工作区为空"),
    SAVE_FAILED(1002, "持久化失败"),
    PARSE_FAILED(1003, "解析失败"),
    CANNOT_UNDO(1004, "不支持 undo 操作"),
    CANNOT_REDO(1005, "不支持 redo 操作"),
    NOT_SUPPORT(1006, "暂不支持该操作"),
    LOST(1007, "缺少参数"),;

    private long code;
    private String message;

    private CommandErrorCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
