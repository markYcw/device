package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/10
 */
@Data
public class StartRecResponseVo implements Serializable {

    @ApiModelProperty("视频rtcp地址，开始浏览成功后返回")
    private VideoRtcp videoRtcp;

    @ApiModelProperty("音频rtcp地址，开始浏览成功后返回")
    private AudioRtcp audioRtcp;

}
