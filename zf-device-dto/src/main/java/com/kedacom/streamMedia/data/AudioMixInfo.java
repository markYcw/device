package com.kedacom.streamMedia.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:57
 */
@Data
public class AudioMixInfo implements Serializable {

    @ApiModelProperty("混音ID")
    private String mixID;

    @ApiModelProperty("混音成员资源ID")
    private List<AudioMixer> audioMixerList;

}
