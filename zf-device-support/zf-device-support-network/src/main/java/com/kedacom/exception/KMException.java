package com.kedacom.exception;

import java.io.Serializable;

/**
 * 中间件基类异常
 * @author van.shu
 * @create 2021/4/29 17:28
 */
public class KMException extends RuntimeException implements Serializable {


    public KMException() {
        super();
    }

    public KMException(String message) {
        super(message);
    }

    public KMException(String message, Throwable cause) {
        super(message, cause);
    }
}
