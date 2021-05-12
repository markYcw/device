package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 10:28
 */
@Data
@ApiModel("停止音频混音入参")
public class StopAudioMixRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotBlank(message = "混音设备分组id不能为空")
    @ApiModelProperty("混音设备分组id")
    private String GroupID;

    @NotEmpty(message = "混音ID不能为空")
    @ApiModelProperty("混音ID")
    private List<String> mixIDs;

}
