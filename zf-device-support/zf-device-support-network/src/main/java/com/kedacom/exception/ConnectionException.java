package com.kedacom.exception;

/**
 * @author van.shu
 * @create 2021/5/12 13:11
 */
public class ConnectionException extends KMException {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
