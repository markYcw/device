package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 18:45
 */
@Data
@ApiModel("开启录像应答")
public class StartRecResponseVO implements Serializable {

    @ApiModelProperty("设备ID")
    private String deviceId;

    @ApiModelProperty("录像ID")
    private String recordId;

}
