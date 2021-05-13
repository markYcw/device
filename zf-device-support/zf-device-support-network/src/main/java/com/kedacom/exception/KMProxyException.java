package com.kedacom.exception;

/**
 * @author van.shu
 * @create 2021/5/12 13:11
 */
public class KMProxyException extends KMException {

    public KMProxyException(String message) {
        super(message);
    }

    public KMProxyException(String message, Throwable cause) {
        super(message, cause);
    }
}
