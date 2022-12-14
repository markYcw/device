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
    @ApiModelProperty("设备号")
    private String puId;

    /**
     * 平台2.0叫“视频源编号”，平台1.0叫“设备通道号”
     */
    @ApiModelProperty("平台2.0叫“视频源编号”，平台1.0叫“设备通道号")
    private Integer sn;

    /**
     * 视频源名称/通道名称
     */
    @ApiModelProperty("视频源名称/通道名称")
    private String name;

    @ApiModelProperty("国标ID")
    private String gbId;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用 0:不启用，1：启用")
    private Integer enable = 0;

    /**
     * 是否在线
     */
    @ApiModelProperty("是否在线 0：离线 1：在线")
    private Integer online = 0;

    /**
     * 是否平台录像。true正在平台录像,false不在平台录像，
     */
    @ApiModelProperty("是否平台录像。0: 未知 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer platRecord = 1;

    /**
     * 是否前端录像。true正在前端录像,false不在前端录像，
     */
    @ApiModelProperty("是否平台录像。0: 未知 1：空闲 2：录像 3：尝试中 4：停止中")
    private Integer puRecord = 1;

    /**
     * uuid 前端用后端无需理会此字段
     */
    @ApiModelProperty("uuid")
    private String uuid;

    /**
     * rid
     */
    @ApiModelProperty("rid")
    private Integer rid;
}
