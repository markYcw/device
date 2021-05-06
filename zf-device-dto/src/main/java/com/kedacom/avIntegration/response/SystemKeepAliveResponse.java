package com.kedacom.avIntegration.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 18:44
 */
@Data
@ApiModel("鉴权保活应答")
public class SystemKeepAliveResponse implements Serializable {

    @ApiModelProperty("响应状态码")
    private Integer error;

}
