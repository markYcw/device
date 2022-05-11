package com.kedacom.power.entity;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "ok", data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>(500, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
