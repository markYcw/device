package com.kedacom.device.stream.request;

import com.kedacom.streamMedia.info.DrawText;
import com.kedacom.streamMedia.info.VideoMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@Data
@ApiModel("开始画面合成业务交互参数")
public class StartVideoMixDTO extends StreamMediaDTO {

    private static final String COMMAND = "startvideomix";

    @ApiModelProperty("画面合成设备分组id")
    private String GroupID;

    @ApiModelProperty("画面合成风格")
    private Integer layout;

    @ApiModelProperty("每个窗口中的图像是否填充满画面窗口\n" +
            "0（默认） - 不填充满画面窗口每个窗口按照原始比例进行显示；\n" +
            "1 - 填充满画面窗口（可能会出现图像宽高比失调）\n")
    private Integer full_window;

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

    @Override
    String getCommand() {
        return COMMAND;
    }

}
