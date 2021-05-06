package com.kedacom.avIntegration.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:45
 */
@Data
@ApiModel("鉴权登出应答")
public class SystemLogOutResponse implements Serializable {

    @ApiModelProperty("响应状态码")
    private Integer error;

}
