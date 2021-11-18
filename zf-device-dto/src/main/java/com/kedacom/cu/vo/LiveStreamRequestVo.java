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
    @ApiModelProperty(value = "发布类型, 1：rtsp 2: rtmp 3: webrtc 4: httpflv(http) 5: httpflv-ws(websocket)", required = true)
    private Integer type;

    @NotNull
    @ApiModelProperty(value = "设备类型, 0：1.0监控平台; 2：3带高清终端; 4：5.0会议平台; 11：5.0终端", required = true)
    private Integer devtype;

    @NotNull
    @ApiModelProperty(value = "校验媒体类型, 0:校验音频和视频; 1:只校验视频", required = true)
    private Integer checkmediatype;

    @NotBlank
    @ApiModelProperty(value = "ip地址", required = true)
    private String ip;

    @NotNull
    @ApiModelProperty(value = "端口", required = true)
    private Integer port;

    @NotBlank
    @ApiModelProperty(value = "登录账户", required = true)
    private String user;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String pwd;

    @NotBlank
    @ApiModelProperty(value = "监控平台：PUID号 :  会议平台：会议号", required = true)
    private String src;

    @NotBlank
    @ApiModelProperty(value = "1 监控平台：通道号 2 会议终端：码流类型 3 会议平台：与会终端", required = true)
    private String chn;

    @ApiModelProperty("5.0以上会议平台用，其它可以不填写")
    private String key;

    @ApiModelProperty(value = "5.0以上会议平台用，其它可以不填写")
    private String secret;

    @ApiModelProperty("组信息")
    private String group;

}
