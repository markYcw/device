package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    @ApiModelProperty(value = "监控设备填国标id、会议或者虚拟终端填设备id(此时background_pic_param里面需要填设备类型)、其余填资源id", required = true)
    private String device_id;

    @ApiModelProperty(value = "录像保存天数(不填就一直保存;填就按填的天数保存,填了30天,一个月后录像就会被覆盖;长期保存就不要填,没有特殊要求,就不要填)")
    private Integer valid_day;

    @ApiModelProperty(value = "分辨率(1080P/720P)(不填默认720P,没有特殊要求,就不要填)")
    private String resolution;

    @ApiModelProperty("MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty("是否叠加背景，0：不叠加，1：叠加")
    private Integer overlay_background;

    @ApiModelProperty("会议或者虚拟终端填设备id需要填音频设备叠加图片参数")
    private BackgroundPicParam background_pic_param;

}
