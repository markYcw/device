package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 17:17
 */
@Data
@ApiModel(description = "录像任务保活入参")
public class MeetRecKeepAliveDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "录像ID不能为空")
    @ApiModelProperty("录像ID")
    private String recordId;

}
