package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/12 15:42
 */
@Data
@ApiModel("开启音频混音应答")
@ToString(callSuper = true)
public class StartAudioMixResponse extends BaseResponse {

    @ApiModelProperty("混音ID")
    private String mixID;

}
