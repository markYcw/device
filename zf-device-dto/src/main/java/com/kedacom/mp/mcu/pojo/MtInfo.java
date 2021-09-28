package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 11:11
 * @description 终端
 */
@Data
@ApiModel(description =  "终端")
public class MtInfo implements Serializable {

    @ApiModelProperty(value = "终端ID")
    private String mtId;

    @ApiModelProperty(value = "终端别名 最大字符长度：128个字节")
    private String alias;

    @ApiModelProperty(value = "终端ip")
    private String ip;

    @ApiModelProperty(value = "是否在线 0-否； 1-是；")
    private Integer online;

    @ApiModelProperty(value = "终端E164号")
    private String e164;

    @ApiModelProperty(value = "终端类型 1-普通终端； 3-电话终端； 5-卫星终端； 7-下级会议； 8-上级会议；")
    private Integer type;

    @ApiModelProperty(value = "呼叫码率")
    private Integer bitrate;

    @ApiModelProperty(value = "是否静音 0-否； 1-是；")
    private Integer silence;

    @ApiModelProperty(value = "是否哑音 0-否； 1-是；")
    private Integer mute;

    @ApiModelProperty(value = "终端号")
    private Integer dual;

    @ApiModelProperty(value = "是否在混音 0-否； 1-是；")
    private Integer mix;

    @ApiModelProperty(value = "是否在合成 0-否；1-是；")
    private Integer vmp;

    @ApiModelProperty(value = "是否在选看 0-否； 1-是；")
    private Integer inspection;

    @ApiModelProperty(value = "是否在录像 0-否； 1-是；")
    private Integer rec;

    @ApiModelProperty(value = "是否在轮询 0-否； 1-是；")
    private Integer poll;

    @ApiModelProperty(value = "是否在上传 0-否； 1-是；")
    private Integer upload;

    @ApiModelProperty(value = "呼叫协议 0-323； 1-sip；")
    private Integer protocol;

    @ApiModelProperty(value = "呼叫模式 0-手动； 2-自动；3-追呼；")
    private Integer callMode;

}
