package com.kedacom.exception;

/**
 * @author van.shu
 * @create 2021/5/12 13:11
 */
public class KMTimeoutException extends KMException {

    public KMTimeoutException(String message) {
        super(message);
    }

    public KMTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
