package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/15
 */
@Data
public class QueryVideoCalendarDto implements Serializable {

    @ApiModelProperty("设备域")
    private String domain;

    @ApiModelProperty("设备id")
    private String puId;

    @ApiModelProperty("通道号")
    private Integer chnId;

    @ApiModelProperty("录像开始时间, eg : 202111051200")
    private String startTime;

    @ApiModelProperty("录像结束时间, eg : 202111051200")
    private String endTime;

    @ApiModelProperty("录像类别： 1：平台录像; 2：前端录像; 3：本地录像")
    private Integer recType;

}
