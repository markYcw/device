package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:11
 */
@Data
@ApiModel("开始画面合成应答")
public class StartVideoMixResponseVO implements Serializable {

    @ApiModelProperty("画面合成设备分组id")
    private String GroupID;

    @ApiModelProperty("画面合成ID")
    private String mixID;

}
