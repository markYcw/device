package com.kedacom.avIntegration.response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 10:13
 */
@Data
@Api("显控服务获取版本应答")
public class DisplayControlVersionResponse {

    @ApiModelProperty("版本")
    private String version;

}
