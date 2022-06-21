package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 16:17
 */
@Data
@ApiModel(description = "停止会议录像入参")
public class UpdateMeetRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "录像ID不能为空")
    @ApiModelProperty(value = "录像ID", required = true)
    private String record_id;

    @ApiModelProperty(value = "设备ID")
    private String device_id;

}
