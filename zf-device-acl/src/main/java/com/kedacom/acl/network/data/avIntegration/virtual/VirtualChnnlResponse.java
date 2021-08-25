package com.kedacom.acl.network.data.avIntegration.virtual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 18:17
 */
@Data
@ApiModel(description = "录入输入通道列表应答")
public class VirtualChnnlResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

}
