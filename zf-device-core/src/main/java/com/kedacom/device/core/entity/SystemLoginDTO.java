package com.kedacom.device.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("鉴权登录入参")
public class SystemLoginDTO implements Serializable {

    /**
     * 用户名 必填
     */
    @ApiModelProperty(value = "用户名 - 必填")
    private String user_name;

    /**
     * 密码 必填
     */
    @ApiModelProperty(value = "密码 - 必填")
    private String password;

}