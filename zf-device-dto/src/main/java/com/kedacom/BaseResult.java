package com.kedacom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回类
 *
 * @author van.shu
 * @create 2021/4/23 13:37
 */
@ApiModel("统一结果")
public class BaseResult<T> implements Serializable {

    @ApiModelProperty("错误码，0为结果正常；非0为结果异常，具体异常码和异常信息请参考接口文档")
    private int errCode;

    @ApiModelProperty("错误描述信息，当错误码是0时，该字段值为空")
    private String errMsg;

    @ApiModelProperty("具体结果数据")
    private T data;

    public static <T> BaseResult<T> succeed(String errMsg) {
        return builder(0, errMsg, null);
    }

    public static <T> BaseResult<T> succeed(String errMsg, T data) {
        return builder(0, errMsg, data);
    }

    public static <T> BaseResult<T> succeed(T data) {
        return builder(0, null, data);
    }

    public static <T> BaseResult<T> failed(String errMsg) {
        return builder(0, errMsg, null);
    }

    public static <T> BaseResult<T> failed(int errCode, String errMsg) {
        return builder(errCode, errMsg, null);
    }

    public static <T> BaseResult<T> failed(int errCode, String errMsg, T data) {
        return builder(errCode, errMsg, data);
    }

    private static <T> BaseResult<T> builder(int errCode, String errMsg, T data) {
        return new BaseResult<>(errCode, errMsg, data);
    }

    public BaseResult(int errCode, String errMsg, T data) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }
}
