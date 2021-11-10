package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
public class AudioRtcp implements Serializable {

    @ApiModelProperty("rtcp地址")
    private String dstIp;

    @ApiModelProperty("rtcp端口")
    private Integer dstPort;

}
