package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 10:14
 */
@Data
public class StartAudioMixResponseVO implements Serializable {

    @ApiModelProperty("混音ID，20位字符")
    private String mixID;

}
