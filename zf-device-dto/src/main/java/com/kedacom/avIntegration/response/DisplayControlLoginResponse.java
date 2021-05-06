package com.kedacom.avIntegration.response;

import io.swagger.annotations.Api;
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
public class DisplayControlLoginResponse implements Serializable {

    @ApiModelProperty("令牌")
    private String token;

}
