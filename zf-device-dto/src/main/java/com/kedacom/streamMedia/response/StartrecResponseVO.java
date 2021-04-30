package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 18:45
 */
@Data
public class StartrecResponseVO implements Serializable {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("录像ID")
    private String recordId;

}
