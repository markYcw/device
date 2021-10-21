package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
public class AiBoxFeatureLibDto implements Serializable {

    @ApiModelProperty("ID")
    private String ID;

    @ApiModelProperty("姓名")
    private String Name;

    @ApiModelProperty("底库图片")
    private String FeatureLibImg;

}
