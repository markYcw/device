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
 * @Date: 2021/4/30 14:27
 */
@Data
@ApiModel("停止画面合成入参")
public class StopVideoMixRequest implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "画面合成设备分组id不能为空")
    @ApiModelProperty("画面合成设备分组id")
    private String GroupID;

    @NotEmpty(message = "画面合成ID不能为空")
    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

}
