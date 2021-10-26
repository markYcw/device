package com.kedacom.aiBox.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/18
 */
@Data
@ApiModel(description = "图片对比请求参数类")
public class ContrastRequestDto implements Serializable {

    @ApiModelProperty("ID")
    @NotEmpty(message = "ID不能为空")
    private String id;

    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("实时图片")
    @NotEmpty(message = "实时图片不能为空")
    private String realTimeImg;

    @ApiModelProperty("底库图片")
    @NotEmpty(message = "底库图片不能为空")
    private String featureLibImg;

    @ApiModelProperty("设备ID")
    @NotEmpty(message = "设备ID不能为空")
    private String abId;

}
