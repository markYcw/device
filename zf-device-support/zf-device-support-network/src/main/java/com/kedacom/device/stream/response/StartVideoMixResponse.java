package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:11
 */
@Data
@ApiModel("开始画面合成应答")
@ToString(callSuper = true)
public class StartVideoMixResponse extends BaseResponse {

    @ApiModelProperty("画面合成ID")
    private String mixID;

}
