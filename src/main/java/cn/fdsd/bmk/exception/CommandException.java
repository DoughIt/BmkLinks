package cn.fdsd.bmk.exception;

/**
 * 自定义异常
 *
 * @author Jerry Zhang
 * create: 2022-11-02 10:58
 */
public class CommandException extends RuntimeException {
    private ErrorCode errorCode;

    public CommandException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
