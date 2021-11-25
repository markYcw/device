package com.kedacom.cu.dto;

import com.kedacom.cu.constant.StreamType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
public class LiveStreamDto implements Serializable {

    private String id;

    /**
     * 发布类型
     * 1：rtsp
     * 2: rtmp
     * 3: webrtc
     */
    private Integer type= StreamType.WEBRTC;

    /**
     * 设备类型
     * 0：1.0监控平台
     * 2：3带高清终端
     * 4：5.0会议平台
     * 11：5.0终端
     */
    private Integer devtype;

    /**
     * 校验媒体类型
     * 0:校验音频和视频
     * 1:只校验视频
     */
    private Integer checkmediatype = 1;

    private String ip;

    private Integer port;

    private String user;

    private String pwd;

    /**
     * 监控平台：PUID号
     * 会议平台：会议号
     */
    private String src;

    /**
     * 监控平台：通道号
     * 会议终端：码流类型
     * 会议平台：与会终端
     */
    private String chn;

    /**
     * 5.0以上会议平台用，其它可以不填写
     */
    private String key;

    /**
     * 5.0以上会议平台用，其它可以不填写
     */
    private String secret;

    /**
     * 组信息
     */
    private String group;

}
