package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.data.DrawText;
import com.kedacom.streamMedia.data.VideoMixer;
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

    @ApiModelProperty("画布内容")
    private DrawText drawText;

    @ApiModelProperty("参与画面合成的终端ID(最大数目为 25路，实际路数依赖硬件性能)")
    private List<VideoMixer> mixerList;

}
