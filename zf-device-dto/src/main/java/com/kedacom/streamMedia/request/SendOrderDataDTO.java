package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/6/9 10:12
 */
@Data
@ApiModel(value = "发送宏指令数据")
public class SendOrderDataDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备国标id不能为空")
    @ApiModelProperty(value = "设备国标id", required = true)
    private String DeviceID;

    @NotBlank(message = "宏指令不能为空")
    @ApiModelProperty(value = "宏指令", required = true)
    private String OrderData;

}
