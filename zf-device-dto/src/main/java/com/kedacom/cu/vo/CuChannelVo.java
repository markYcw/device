package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备通道/视频源 信息
 * @author ycw
 * @create 2020/11/17 18:39
 */
@Data
public class CuChannelVo {

    /**
     * 设备编号
     */
    @ApiModelProperty("设备编号")
    private String puid;

    /**
     * 平台2.0叫“视频源编号”，平台1.0叫“设备通道号”
     */
    @ApiModelProperty("平台2.0叫“视频源编号”，平台1.0叫“设备通道号")
    private int sn;

    /**
     * 视频源名称/通道名称
     */
    @ApiModelProperty("视频源名称/通道名称")
    private String name;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private boolean enable = true;

    /**
     * 是否在线
     */
    @ApiModelProperty("是否在线")
    private boolean online;

    /**
     * 是否平台录像。true正在平台录像,false不在平台录像，
     */
    @ApiModelProperty("是否平台录像。true正在平台录像,false不在平台录像，")
    private boolean platRecord;

    /**
     * 是否前端录像。true正在前端录像,false不在前端录像，
     */
    @ApiModelProperty("是否前端录像。true正在前端录像,false不在前端录像，")
    private boolean puRecord;

    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * rid
     */
    @ApiModelProperty("rid")
    private Integer rid;
}
