package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.streamMedia.info.BackgroundPicParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author  hxj
 * @date  2021/5/13 16:44
 */
@Data
@ToString(callSuper = true)
public class StartMeetRecRequest extends BaseRequest {

    private static final String COMMAND = "startrecm";

    @ApiModelProperty("登录token")
    private String account_token;

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @NotBlank(message = "设备ID不能为空")
    @ApiModelProperty(value = "监控设备填国标id、会议或者虚拟终端填设备id(此时background_pic_param里面需要填设备类型)、其余填资源id", required = true)
    private String device_id;

    @ApiModelProperty(value = "录像保存天数(不填就一直保存;填就按填的天数保存,填了30天,一个月后录像就会被覆盖;长期保存就不要填,没有特殊要求,就不要填)")
    private Integer valid_day;

    @ApiModelProperty(value = "分辨率(1080P/720P)(不填默认720P,没有特殊要求,就不要填)")
    private String resolution;

    @ApiModelProperty(value = "MIX、MIX_AUDIO、MIX_ALL")
    private String mix;

    @ApiModelProperty(value = "是否叠加背景，0：不叠加，1：叠加")
    private Integer overlay_background;

    @ApiModelProperty(value = "会议或者虚拟终端填设备id需要填音频设备叠加图片参数")
    private BackgroundPicParam background_pic_param;

    @ApiModelProperty(value = "是否异步，默认为0，同步接口；1为异步接口")
    private Integer async;

    @ApiModelProperty(value = "保活时长，单位秒，默认为0，不保活；支持时长区间[30,600]")
    private Integer keepalive;

    @Override
    public String name() {
        return COMMAND;
    }

}
