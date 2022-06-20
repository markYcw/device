package com.kedacom.streamMedia.request;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:56
 * @description
 */
@ApiModel(description = "控制音频功率上报")
@Data
public class GetBurnStateDTO implements Serializable {

    @NotBlank(message = "平台id不能为空")
    @ApiModelProperty(value = "平台id", required = true)
    private String umsId;

    @NotBlank(message = "设备id不能为空")
    @JSONField(name = "deviceId")
    @ApiModelProperty(value = "设备ID", required = true)
    private String deviceID;

}
