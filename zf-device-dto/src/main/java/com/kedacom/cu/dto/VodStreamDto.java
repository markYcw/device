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
public class VodStreamDto implements Serializable {

    /**
     * 用户填写的唯一标识
     */
    private String id;


    private Integer type= StreamType.WEBRTC;

    /**
     * 设备类型
     * 0：1.0监控平台
     * 2：3带高清终端
     * 4：5.0会议平台
     * 11：5.0终端
     * 目前好像1.0平台有效，待验证
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
     * 开始时间 如：20201116080000
     */
    private String start;

    /**
     * 结束时间 如：20201117140000
     */
    private String end;

    /**
     * 录像来源（监控平台有用）
     * 1：当前平台
     * 2：设备所在平台
     * 3：前端
     */
    private Integer recsrc;

    /**
     * 组信息
     */
    private String group;

}
