package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/3/29 14:28
 */
@Data
@ApiModel(description = "停止录像入参")
public class UpdateRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "录像ID不能为空")
    @ApiModelProperty(value = "录像ID", required = true)
    private String record_id;

    @NotBlank(message = "设备ID不能为空")
    @ApiModelProperty(value = "监控设备填国标id、会议或者虚拟终端填设备id(此时background_pic_param里面需要填设备类型)、其余填资源id", required = true)
    private String device_id;

}
