package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.info.DrawBorder;
import com.kedacom.streamMedia.info.DrawText;
import com.kedacom.streamMedia.info.VideoMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:11
 */
@Data
@ApiModel(description = "开始画面合成入参")
public class StartVideoMixDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty("画面合成风格")
    private Integer layout;

    @ApiModelProperty("每个窗口中的图像是否填充满画面窗口\n" +
            "0（默认） - 不填充满画面窗口每个窗口按照原始比例进行显示；\n" +
            "1 - 填充满画面窗口（可能会出现图像宽高比失调）\n")
    private Integer full_window;

    @ApiModelProperty(value = "开启任务保活功能，单位:秒。\n" +
            "范围：30-600（秒），推荐60秒。\n" +
            "默认0：不开启任务保活功能；\n" +
            "保活时间小于30秒，实际生效30秒；\n" +
            "保活时间大于600秒时，实际生效600秒；\n" +
            "不设置保活时，需要客户端主动去结束该合成任务。")
    private Integer keepalive;

    @ApiModelProperty("是否支持非语音激励场景下，画面合成第一个通道克隆某一其他通道资源:0、不克隆;1、克隆1通道的通道资源;2、克隆2通道的通道资源")
    private Integer clone_window;

    @ApiModelProperty("是否支持语音激励VIP合成:0-不支持，默认;1-支持语音激励")
    private Integer vad;

    @ApiModelProperty("是否开启音频混音:0-不开启（默认）;1-开启")
    private Integer audio_mix;

    @ApiModelProperty("画布内容")
    private DrawText draw_text;

    @ApiModelProperty("参与画面合成的终端ID(最大数目为 25路，实际路数依赖硬件性能)")
    private List<VideoMixer> mixer_list;

    @ApiModelProperty(value = "绘制边框")
    private DrawBorder draw_border;

    @ApiModelProperty(value = "会话管控，0-不管控，1-管控")
    private Integer session_control;
}
