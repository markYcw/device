package com.kedacom.exception;

/**
 * @author van.shu
 * @create 2021/5/17 11:12
 */
public class ParseDataException extends RuntimeException{

    public ParseDataException(String message) {
        super(message);
    }

    public ParseDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
