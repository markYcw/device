package com.kedacom.mt.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class GetMtStatusResponseVo implements Serializable {

    @ApiModelProperty("0：非哑音， 1：哑音")
    private Integer mute;

    @ApiModelProperty("0：非静音， 1：静音")
    private Integer quiet;

    @ApiModelProperty("0：不参加混音， 1：参加混音")
    private Integer mix;

    @ApiModelProperty("0：GK注册失败， 1：GK注册成功")
    private Integer gkStat;

    @ApiModelProperty("0：不允许远程， 1：允许远程")
    private Integer feccEnable;

    @ApiModelProperty("0：第一路无视频， 1：第一路有视频")
    private Integer pvVideo;

    @ApiModelProperty("0：第二路无视频， 1：第二路有视频")
    private Integer svVideo;

    @ApiModelProperty("0：不在会议中， 1：在会议中")
    private Integer confStatus;

}
