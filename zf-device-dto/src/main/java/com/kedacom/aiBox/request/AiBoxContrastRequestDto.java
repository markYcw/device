package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "图片对比AIBox请求参数类")
public class AiBoxContrastRequestDto implements Serializable {

    @ApiModelProperty("实时图片")
    private String RealTimeImg;

    @ApiModelProperty("底库图片信息集合")
    private List<AiBoxFeatureLibDto> FeatureLibImgList;

}
