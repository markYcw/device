package com.kedacom.device.stream.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/12 15:42
 */
@Data
@ApiModel("开启音频混音应答")
public class StartAudioMixResponse extends StreamMediaResponse {

    @ApiModelProperty("混音ID")
    private String mixID;

}
