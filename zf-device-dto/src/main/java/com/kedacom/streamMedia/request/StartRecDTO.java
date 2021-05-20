package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 18:45
 */
@Data
@ApiModel("开启录像入参")
public class StartRecDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "设备ID不能为空")
    @ApiModelProperty(value = "监控设备填国标id、会议或者虚拟终端填设备id(此时background_pic_param里面需要填设备类型)、其余填资源id",required = true)
    private String device_id;

    @NotNull(message = "录像保存天数不能为空")
    @ApiModelProperty(value = "录像保存天数",required = true)
    private Integer valid_day;

    @NotBlank(message = "分辨率不能为空")
    @ApiModelProperty(value = "分辨率(1080P/720P)",required = true)
    private String resolution;

    @ApiModelProperty("MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty("是否叠加背景，0：不叠加，1：叠加")
    private Integer overlay_background;

    @ApiModelProperty("音频设备叠加图片参数overlay_background=1 时生效")
    private BackgroundPicParam background_pic_param;

}
