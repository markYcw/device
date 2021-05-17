package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
public class MediaVo implements Serializable {

    @ApiModelProperty(value = "当前成员的音频源")
    private AudioVo AudioSource;

    @ApiModelProperty(value = "当前成员的视频源")
    private VideoVo VideoSource;

    @ApiModelProperty(value = "当前成员的双流源")
    private DualStreamVo DualStreamSource;

}
