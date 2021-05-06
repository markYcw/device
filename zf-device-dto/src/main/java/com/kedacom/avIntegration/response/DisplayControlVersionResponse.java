package com.kedacom.avIntegration.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 10:13
 */
@Data
@ApiModel("鉴权获取版本应答")
public class DisplayControlVersionResponse implements Serializable {

    @ApiModelProperty("版本")
    private String version;

}
