package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 19:34
 */
@Data
@ApiModel("停止录像入参")
public class StopRecRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotBlank(message = "录像ID不能为空")
    @ApiModelProperty("录像ID")
    private String record_id;

}
