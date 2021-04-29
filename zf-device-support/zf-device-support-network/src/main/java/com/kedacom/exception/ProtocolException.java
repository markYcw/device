package com.kedacom.exception;

import java.io.Serializable;

/**
 * @author van.shu
 * @create 2021/4/26 11:10
 */
public class ProtocolException extends RuntimeException implements Serializable {

    public ProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtocolException(String message) {
        super(message);
    }

}
