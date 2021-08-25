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
 * @Date: 2021/4/30 13:55
 */
@Data
@ApiModel(description = "查询混音信息入参")
public class QueryAudioMixDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "混音设备分组id不能为空")
    @ApiModelProperty("混音设备分组id")
    private String groupID;

    @NotEmpty(message = "混音ID集合不能为空")
    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;

}
