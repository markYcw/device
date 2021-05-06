package com.kedacom.avIntegration.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 09:38
 */
@Data
@Api("显控服务登录应答")
public class DisplayControlLoginResponse {

    @ApiModelProperty("令牌")
    private String token;

}
