package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
public class OperateLockingRecDto implements Serializable {

    @ApiModelProperty("操作类型, 0:开启; 1:关闭")
    private Integer type;

    @ApiModelProperty("设备域")
    private String domain;

    @ApiModelProperty("设备id")
    private String puId;

    @ApiModelProperty("通道号")
    private Integer chn;

    @ApiModelProperty("录像开始时间, eg : 202111051200, 开始时必填")
    private String startTime;

    @ApiModelProperty("录像结束时间, eg : 202111051200, 开始时必填")
    private String endTime;

}
