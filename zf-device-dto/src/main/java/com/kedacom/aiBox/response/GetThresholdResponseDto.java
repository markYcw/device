package com.kedacom.aiBox.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/1
 */
@Data
@ApiModel(description = "获取远程AIBox人脸对比阈值响应参数类")
public class GetThresholdResponseDto implements Serializable {

    @ApiModelProperty("设备IP")
    private String abIp;

    @ApiModelProperty("设备名称")
    private String abName;

    @ApiModelProperty("识别的人脸最小像素值")
    private String minFace;

    @ApiModelProperty("识别的人脸最大像素值")
    private String maxFace;

}
