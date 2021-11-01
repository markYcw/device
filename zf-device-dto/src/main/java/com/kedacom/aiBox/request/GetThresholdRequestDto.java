package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/1
 */
@Data
@ApiModel(description = "获取远程AIBox人脸对比阈值请求参数类")
public class GetThresholdRequestDto implements Serializable {

    @ApiModelProperty("设备id")
    @NotEmpty(message = "设备id不能为空")
    private String abId;

}
