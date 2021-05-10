package com.kedacom.avIntegration.vo.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/10 15:32
 */
@Data
@ApiModel("鉴权登录返回参数")
public class SystemLoginVO implements Serializable {

    @ApiModelProperty("令牌")
    private String token;

}
