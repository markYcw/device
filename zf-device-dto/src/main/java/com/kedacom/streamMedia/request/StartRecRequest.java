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
public class StartRecRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotBlank(message = "设备ID不能为空")
    @ApiModelProperty("具体设备ID")
    private String device_id;

    @NotNull(message = "录像保存天数不能为空")
    @ApiModelProperty("录像保存天数")
    private Integer valid_day;

    @NotBlank(message = "分辨率不能为空")
    @ApiModelProperty("分辨率(1080P/720P)")
    private String resolution;

    @ApiModelProperty("MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty("是否叠加背景，0：不叠加，1：叠加")
    private Integer overlay_background;

    @ApiModelProperty("音频设备叠加图片参数overlay_background=1 时生效")
    private BackgroundPicParam background_pic_param;

}
