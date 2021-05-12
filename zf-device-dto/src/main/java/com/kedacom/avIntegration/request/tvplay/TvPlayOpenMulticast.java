package com.kedacom.avIntegration.request.tvplay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TvPlayOpenMulticast implements Serializable {

    @ApiModelProperty(value = "组播地址")
    private String ip;

    @ApiModelProperty(value = "组播端口")
    private Integer port;

}
