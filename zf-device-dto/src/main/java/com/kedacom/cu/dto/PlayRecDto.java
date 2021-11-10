package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
public class PlayRecDto implements Serializable {

    @ApiModelProperty("操作类型，0：开始; 1：停止")
    private Integer type;

    @ApiModelProperty("目的ip")
    private String dstIp;

    @ApiModelProperty("目的端口")
    private Integer dstPort;

    @ApiModelProperty("puId，开始时必填")
    private String puId;

    @ApiModelProperty("通道号，开始时必填")
    private Integer chn;

    @ApiModelProperty("录像开始时间, 如：202111051200, 开始时必填")
    private String startTime;

    @ApiModelProperty("录像开始时间, 如：202111051200, 开始时必填")
    private String endTime;

    @ApiModelProperty("录像类型，1：平台录像, 2：前端录像, 3：本地录像, 开始时必填")
    private Integer recType;

}
