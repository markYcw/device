package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.data.DrawText;
import com.kedacom.streamMedia.data.VideoMixer;
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
@ApiModel("开始画面合成入参")
public class StartVideoMixRequestDTO implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @ApiModelProperty("画面合成风格")
    private Integer layout;

    @ApiModelProperty("每个窗口中的图像是否填充满画面窗口\n" +
            "0（默认） - 不填充满画面窗口每个窗口按照原始比例进行显示；\n" +
            "1 - 填充满画面窗口（可能会出现图像宽高比失调）\n")
    private Integer fullWindow;

    @ApiModelProperty("是否支持非语音激励场景下，画面合成第一个通道克隆某一其他通道资源:0、不克隆;1、克隆1通道的通道资源;2、克隆2通道的通道资源")
    private Integer cloneWindow;

    @ApiModelProperty("是否支持语音激励VIP合成:0-不支持，默认;1-支持语音激励")
    private Integer vad;

    @ApiModelProperty("是否开启音频混音:0-不开启（默认）;1-开启")
    private Integer audioMix;

    @ApiModelProperty("画布内容")
    private DrawText drawText;

    @ApiModelProperty("参与画面合成的终端ID(最大数目为 25路，实际路数依赖硬件性能)")
    private List<VideoMixer> mixerList;

}
