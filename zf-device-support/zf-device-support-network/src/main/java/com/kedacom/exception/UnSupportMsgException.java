package com.kedacom.exception;

/**
 * @author van.shu
 * @create 2021/5/13 19:42
 */
public class UnSupportMsgException extends KMException {

    public UnSupportMsgException(String message) {
        super(message);
    }

    public UnSupportMsgException(String message, Throwable cause) {
        super(message, cause);
    }
}
