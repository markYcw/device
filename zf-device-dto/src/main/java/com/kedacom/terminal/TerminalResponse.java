package com.kedacom.terminal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/7/30 9:16
 * @Description 终端返回的统一结果
 */
@Data
@ApiModel("终端的统一返回")
public class TerminalResponse<T> implements Serializable {

    @ApiModelProperty(value = "错误码，0表示成功")
    private Integer code;

    @ApiModelProperty(value = "错误描述")
    private String message;

    @ApiModelProperty(value = "结果")
    private T data;

}
