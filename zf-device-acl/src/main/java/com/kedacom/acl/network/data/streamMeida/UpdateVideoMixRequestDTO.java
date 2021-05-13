package com.kedacom.acl.network.data.streamMeida;

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
 * @Date: 2021/4/30 14:32
 */
@Data
@ApiModel("更新画面合成业务交互参数")
public class UpdateVideoMixRequestDTO implements Serializable {

    @NotBlank(message = "使用32位UUID不能为空")
    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @NotBlank(message = "画面合成ID不能为空")
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

}
