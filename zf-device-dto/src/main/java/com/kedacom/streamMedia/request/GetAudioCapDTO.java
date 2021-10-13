package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 11:02
 * @description
 */
@ApiModel(description = "获取音频能力集")
@Data
public class GetAudioCapDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceID;


}
