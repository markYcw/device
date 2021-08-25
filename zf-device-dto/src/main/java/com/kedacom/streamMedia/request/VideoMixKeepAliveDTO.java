package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/18 14:51
 * @description 合成画面保活入参
 */
@Data
@ApiModel(description =  "合成画面保活入参")
public class VideoMixKeepAliveDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "画面合成设备分组id不能为空")
    @ApiModelProperty("画面合成设备分组id")
    private String groupID;

    @NotEmpty(message = "画面合成ID不能为空")
    @ApiModelProperty("画面合成ID")
    private List<String> mixIDs;

}
