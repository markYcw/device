package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/12 15:42
 */
@Data
@ApiModel(description = "开启音频混音应答")
public class StartAudioMixResponseVO implements Serializable {

    @ApiModelProperty("混音设备分组id")
    private String groupID;

    @ApiModelProperty("混音ID")
    private String mixID;

}
