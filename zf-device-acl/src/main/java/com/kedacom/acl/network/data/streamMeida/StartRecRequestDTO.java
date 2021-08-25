package com.kedacom.acl.network.data.streamMeida;

import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 18:45
 */
@Data
@ApiModel(description = "开启录像业务交互参数")
public class StartRecRequestDTO implements Serializable {

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("具体设备ID")
    private String device_id;

    @ApiModelProperty("录像保存天数")
    private Integer valid_day;

    @ApiModelProperty("分辨率(1080P/720P)")
    private String resolution;

    @ApiModelProperty("MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty("是否叠加背景，0：不叠加，1：叠加")
    private Integer overlay_background;

    @ApiModelProperty("音频设备叠加图片参数overlay_background=1 时生效")
    private BackgroundPicParam background_pic_param;

}
