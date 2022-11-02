package cn.fdsd.bmk.exception;

/**
 * 错误码接口定义
 * @author Jerry Zhang
 * create: 2022-11-02 10:59
 */
public interface ErrorCode {
    long getCode();

    String getMessage();
}
