package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.streamMedia.info.DrawText;
import com.kedacom.streamMedia.info.VideoMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
@ApiModel(description = "更新画面合成业务交互参数")
public class UpdateVideoMixRequest extends BaseRequest {

    private static final String COMMAND = "updatevideomix";

    @ApiModelProperty("画面合成设备分组id")
    @JSONField(name = "GroupID")
    private String groupID;

    @ApiModelProperty("画面合成ID")
    private String mixID;

    @ApiModelProperty("add 添加参与画面合成或接收方的终端ID; delete 删除参与画面合成或接收方的终端ID; update 更新参与画面合成的终端ID（会覆盖之前的配置）")
    private String cmdType;

    @ApiModelProperty("画面合成风格")
    private Integer layout;

    @ApiModelProperty("画面是否拉伸铺满。0-否  1-是")
    private Integer full_window;

    @ApiModelProperty("是否支持语音激励VIP合成:0-不支持，默认;1-支持语音激励")
    private Integer vad;

    @ApiModelProperty("画布内容")
    private DrawText draw_text;

    @ApiModelProperty("参与画面合成的终端ID(最大数目为 25路，实际路数依赖硬件性能)")
    private List<VideoMixer> mixer_list;

    @Override
    public String name() {
        return COMMAND;
    }

}
