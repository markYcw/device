package com.kedacom.avIntegration.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 09:35
 */
@Data
@ApiModel("鉴权基本入参")
public class DisplayControlBaseDTO implements Serializable {

    @NotEmpty(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填")
    private String token;

}
