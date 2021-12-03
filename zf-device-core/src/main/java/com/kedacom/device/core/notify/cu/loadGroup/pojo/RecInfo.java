package com.kedacom.device.core.notify.cu.loadGroup.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/2 19:45
 * @description 录像信息
 */
@Data
public class RecInfo {

    @ApiModelProperty("域编号")
    private String domain;

    @ApiModelProperty("设备号")
    private String puId;


    @ApiModelProperty("通道号")
    private Integer chn;

    @ApiModelProperty("录像开始时间")
    private String startTime;

    @ApiModelProperty("录像结束时间")
    private String endTime;

    @ApiModelProperty("是否锁定 0:否 1:是")
    private Integer isLock;

}
