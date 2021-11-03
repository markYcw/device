package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/3
 */
@Data
public class SetThresholdRequestDto implements Serializable {

    @NotEmpty(message = "设备id不能为你空")
    @ApiModelProperty(value = "设备id")
    private String abId;

    @NotNull(message = "识别的人脸最小像素值不能为空")
    @ApiModelProperty("能识别的人脸最小像素值(默认为60)")
    private Integer minFace;

    @NotNull(message = "识别的人脸最大像素值不能为空")
    @ApiModelProperty("能识别的人脸最大像素值(默认为400)")
    private Integer maxFace;

}
