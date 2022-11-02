package cn.fdsd.bmk.exception;

/**
 * 定义各类返回码的值
 * @author Jerry Zhang
 * create: 2022-11-02 11:00
 */
public enum CommandErrorCode implements ErrorCode{
    CANNOT_UNDO(1001, "不支持 undo 操作"),
    CANNOT_REDO(1002, "不支持 redo 操作");

    private long code;
    private String message;

    private CommandErrorCode(long code, String message){
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
