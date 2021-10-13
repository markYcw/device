package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:30
 * @description
 */
@ApiModel(description = "设置音频功率上报间隔")
@Data
public class SetAudioActIntervalDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceID;

    @NotNull(message = "上报间隔不能为空")
    @ApiModelProperty(value = "上报间隔，单位ms 范围：100-60000 eg:100", required = true)
    private Integer interval;

}
