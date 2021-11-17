package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
@ApiModel(description = "发布点播请求参数类")
public class VodStreamRequestVo implements Serializable {

    @ApiModelProperty("用户填写的唯一标识")
    private String id;

    @NotNull
    @ApiModelProperty("发布类型, 1：rtsp，2: rtmp，3: webrtc")
    private Integer type;

    @NotNull
    @ApiModelProperty("设备类型, 0：1.0监控平台， 2：3带高清终端， 4：5.0会议平台， 11：5.0终端")
    private Integer devtype;

    @NotNull
    @ApiModelProperty("校验媒体类型, 0:校验音频和视频：1:只校验视频")
    private Integer checkmediatype;

    @NotBlank
    @ApiModelProperty("监控平台id")
    private String ip;

    @NotNull
    @ApiModelProperty("监控平台端口")
    private Integer port;

    @NotBlank
    @ApiModelProperty("监控平台登录用户")
    private String user;

    @NotBlank
    @ApiModelProperty("监控平台登录密码")
    private String pwd;

    @NotBlank
    @ApiModelProperty("监控平台：PUID号，会议平台：会议号")
    private String src;

    @NotBlank
    @ApiModelProperty("监控平台：通道号，会议终端：码流类型，会议平台：与会终端")
    private String chn;

    @NotBlank
    @ApiModelProperty("开始时间 如：20201116080000")
    private String start;

    @NotBlank
    @ApiModelProperty("结束时间 如：20201117140000")
    private String end;

    @NotNull
    @ApiModelProperty("录像来源（监控平台有用）：1：当前平台， 2：设备所在平台，3：前端")
    private Integer recsrc;

    @ApiModelProperty("用户填写的唯一标识")
    private String group;

}
