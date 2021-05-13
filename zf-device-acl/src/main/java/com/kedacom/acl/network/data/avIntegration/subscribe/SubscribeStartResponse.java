package com.kedacom.acl.network.data.avIntegration.subscribe;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 10:23
 */
@Data
@ApiModel("订阅应答")
public class SubscribeStartResponse implements Serializable {

    @ApiModelProperty("响应状态码")
    private Integer error;

}
