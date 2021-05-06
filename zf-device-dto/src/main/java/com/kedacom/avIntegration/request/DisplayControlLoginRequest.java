package com.kedacom.avIntegration.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 09:29
 */
@Data
@ApiModel("鉴权登录入参")
public class DisplayControlLoginRequest implements Serializable {

    /**
     * 用户名 必填
     */
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名 - 必填")
    private String user_name;

    /**
     * 密码 必填
     */
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码 - 必填")
    private String password;

}
