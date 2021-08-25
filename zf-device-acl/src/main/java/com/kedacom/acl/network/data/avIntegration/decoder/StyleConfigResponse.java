package com.kedacom.acl.network.data.avIntegration.decoder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:57
 */
@Data
@ApiModel(description =  "设置解码通道风格信息应答")
public class StyleConfigResponse implements Serializable {


    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty("失败时返回信息")
    private String errstr;


}
