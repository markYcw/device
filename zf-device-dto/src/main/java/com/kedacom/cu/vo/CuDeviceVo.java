package com.kedacom.cu.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ycw
 */
@Data
public class CuDeviceVo {
    /**设备类型：编码器*/
    public static final int DEVICE_TYPE_ENCODER = 1;
    /**设备类型：解码器*/
    public static final int DEVICE_TYPE_DECODER = 2;
    /**设备类型：电视墙*/
    public static final int DEVICE_TYPE_TVWALL = 4;
    /**设备类型：NVR*/
    public static final int DEVICE_TYPE_NVR = 5;
    /**设备类型：SVR*/
    public static final int DEVICE_TYPE_SVR = 6;
    /**设备类型：报警主机（告警主机）*/
    public static final int DEVICE_TYPE_ALARMHOST = 7;

    /**
     * 设备所在的平台域的域编号(2.0有效)
     */
    @ApiModelProperty("平台域id")
    private String domain;

    /**
     * 所在的组ID
     * @see
     */
    @ApiModelProperty("所在的组ID")
    private String groupId;

    /**
     * 设备号
     */
    @ApiModelProperty("设备号")
    private String puId;

    @ApiModelProperty("平台1.0对应puId")
    private String puId10;

    @ApiModelProperty("国标ID")
    private String gbId;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String name;

    /**
     * 设备类型
     * <pre>
     * {@link #DEVICE_TYPE_ENCODER} 编码器
     * {@link #DEVICE_TYPE_DECODER} 解码器
     * {@link #DEVICE_TYPE_TVWALL} 电视墙
     * {@link #DEVICE_TYPE_NVR} NVR
     * {@link #DEVICE_TYPE_SVR} SVR
     * {@link #DEVICE_TYPE_ALARMHOST} 报警主机（告警主机）
     * </pre>
     */
    @ApiModelProperty("1编码器 2解码器 4电视墙 5NVR 6SVR 7报警主机")
    private int type;

    /**
     * 权限：（二进制位数） 1：云镜控制(1-10)级
     * 第一位开始，共占用4位。9：前端录像。10：前端放像。11：前端录像删除。12：前端录像下载。17
     * ：前端录像设置。18：前端开关量输出控制。19：布防，撤防。
     */
    @ApiModelProperty("权限：（二进制位数） 1：云镜控制(1-10)级第一位开始，共占用4位。9：前端录像。10：前端放像。11：前端录像删除。12：前端录像下载。17：前端录像设置。18：前端开关量输出控制。19：布防，撤防。 ")
    private int prilevel;

    /**
     * 设备厂商
     */
    @ApiModelProperty("设备厂商")
    private String manufact;

    @ApiModelProperty("编码通道数")
    private Integer encoderChnNum;

    @ApiModelProperty("视频源数")
    private Integer devSrcNum;

    /**
     * 视频源列表
     */
    @ApiModelProperty("视频源列表")
    private List<CuChannelVo> childList = new ArrayList<>();

    @ApiModelProperty("编码通道列表")
    private List<EnChnVo> enChnVos;

    /**
     * 设备是否在线
     */
    @ApiModelProperty("设备是否在线 0：离线 1：在线")
    private Integer online = 0;

    /**
     * 通道总数当且仅当改设备底下存在通道时有此属性
     */
    @ApiModelProperty("通道总数当且仅当改设备底下存在通道时有此属性")
    private Integer count;

    /**
     * 通道在线数当且仅当改设备底下存在通道时有此属性
     */
    @ApiModelProperty("通道在线数当且仅当改设备底下存在通道时有此属性")
    private Integer onLineCount;

    /**
     * uuid 前端用后端无需理会此字段
     */
    @ApiModelProperty("uuid")
    private String uuid;
}
