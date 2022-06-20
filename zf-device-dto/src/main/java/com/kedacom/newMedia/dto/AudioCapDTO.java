package com.kedacom.newMedia.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/05/26 18:17
 * @description
 */
@ApiModel(description = "获取音频能力集")
@Data
public class AudioCapDTO implements Serializable {

    @NotBlank(message = "设备id不能为空")
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceId;


}
