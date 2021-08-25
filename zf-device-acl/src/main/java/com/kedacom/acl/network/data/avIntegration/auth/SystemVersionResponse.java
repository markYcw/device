package com.kedacom.acl.network.data.avIntegration.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 10:13
 */
@Data
@ApiModel(description = "鉴权获取版本应答")
public class SystemVersionResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty("版本")
    private String version;

}
