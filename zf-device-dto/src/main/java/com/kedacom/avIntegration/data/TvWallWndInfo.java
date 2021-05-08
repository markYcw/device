package com.kedacom.avIntegration.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:39
 */
@Data
@ApiModel("大屏-窗口信息")
public class TvWallWndInfo implements Serializable {

    @ApiModelProperty(value = "窗口索引 - 从1开始，从上到下，从左到右排布")
    private Integer cell_index;

    @ApiModelProperty(value = "解码器ID - 统一设备服务分配国标ID")
    private String decid;

    @ApiModelProperty(value = "解码器通道ID - 统一设备服务分配")
    private Integer dec_chn;

    @ApiModelProperty(value = "信号源ID - 统一设备服务分配国标ID")
    private String chnid;


}
