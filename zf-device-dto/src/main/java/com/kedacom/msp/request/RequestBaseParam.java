package com.kedacom.msp.request;

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
public class RequestBaseParam implements Serializable {

    @NotEmpty(message = "token令牌不能为空")
    @ApiModelProperty(value = "token令牌 - 必填", required = true)
    private String token;

}
