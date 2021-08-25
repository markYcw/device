package com.kedacom.msp.request.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 09:29
 */
@Data
@ApiModel(description = "鉴权登录入参")
public class SystemLoginRequest implements Serializable {

    @NotBlank(message = "统一平台id不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

}
