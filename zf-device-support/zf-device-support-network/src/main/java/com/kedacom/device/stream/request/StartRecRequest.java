package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
public class StartRecRequest extends BaseRequest {

    private static final String COMMAND = "startrec";

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

    @Override
    public String name() {
        return COMMAND;
    }

}
