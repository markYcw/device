package com.kedacom.avIntegration.response.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 09:38
 */
@Data
@ApiModel("鉴权登录应答")
public class SystemLoginResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败1")
    private Integer error;

    @ApiModelProperty("令牌")
    private String token;

}
