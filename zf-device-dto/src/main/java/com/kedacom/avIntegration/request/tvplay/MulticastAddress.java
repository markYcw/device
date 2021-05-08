package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:38
 */
@Data
@ApiModel("组播地址信息")
public class MulticastAddress implements Serializable {

    @ApiModelProperty(value = "组播地址")
    private String ip;

    @ApiModelProperty(value = "组播端口")
    private Integer port;

}
