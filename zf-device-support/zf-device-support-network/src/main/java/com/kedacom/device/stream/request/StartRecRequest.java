package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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

    @ApiModelProperty(value = "开启任务保活功能，单位:秒。\n" +
            "范围：30-600（秒），推荐60秒。\n" +
            "默认0：不开启任务保活功能；\n" +
            "保活时间小于30秒，实际生效30秒；\n" +
            "保活时间大于600秒时，实际生效600秒；\n" +
            "不设置保活时，需要客户端主动去结束该合成任务。")
    private Integer keepalive;

    @Override
    public String name() {
        return COMMAND;
    }

}
