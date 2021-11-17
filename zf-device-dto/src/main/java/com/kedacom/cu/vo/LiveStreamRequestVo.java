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
@ApiModel(description = "发布直播请求参数类")
public class LiveStreamRequestVo implements Serializable {

    @ApiModelProperty("用户填写的唯一标识")
    private String id;

    @NotNull
    @ApiModelProperty("发布类型, 1：rtsp 2: rtmp 3: webrtc 4: httpflv(http) 5: httpflv-ws(websocket)")
    private Integer type;

    @NotNull
    @ApiModelProperty("设备类型, 0：1.0监控平台; 2：3带高清终端; 4：5.0会议平台; 11：5.0终端")
    private Integer devtype;

    @NotNull
    @ApiModelProperty("校验媒体类型, 0:校验音频和视频; 1:只校验视频")
    private Integer checkmediatype;

    @NotBlank
    @ApiModelProperty("ip地址")
    private String ip;

    @NotNull
    @ApiModelProperty("端口")
    private Integer port;

    @NotBlank
    @ApiModelProperty("用户名")
    private String user;

    @NotBlank
    @ApiModelProperty("密码")
    private String pwd;

    @NotBlank
    @ApiModelProperty("监控平台：PUID号 :  会议平台：会议号")
    private String src;

    @NotBlank
    @ApiModelProperty("1 监控平台：通道号 2 会议终端：码流类型 3 会议平台：与会终端")
    private String chn;

    @NotBlank
    @ApiModelProperty("5.0以上会议平台用，其它可以不填写")
    private String key;

    @NotBlank
    @ApiModelProperty("5.0以上会议平台用，其它可以不填写")
    private String secret;

    @ApiModelProperty("组信息")
    private String group;

}
