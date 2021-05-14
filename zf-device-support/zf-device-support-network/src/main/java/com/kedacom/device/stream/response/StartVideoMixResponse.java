package com.kedacom.device.stream.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:11
 */
@Data
@ApiModel("开始画面合成应答")
public class StartVideoMixResponse extends StreamMediaResponse {

    @ApiModelProperty("画面合成ID")
    private String mixID;

}