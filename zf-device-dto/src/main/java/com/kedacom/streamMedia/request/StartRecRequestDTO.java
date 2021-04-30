package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.data.BackgroundPicParam;
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
public class StartRecRequestDTO implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @ApiModelProperty("具体设备ID")
    private String deviceId;

    @ApiModelProperty("录像保存天数")
    private Integer validDay;

    @ApiModelProperty("MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty("是否叠加背景，0：不叠加，1：叠加")
    private Integer overlayBackground;

    @ApiModelProperty("分辨率(1080P/720P)")
    private String resolution;

    @ApiModelProperty("音频设备叠加图片参数overlay_background=1 时生效")
    private BackgroundPicParam backgroundPicParam;

}
