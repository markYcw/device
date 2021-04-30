package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.data.AudioMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:35
 */
@Data
@ApiModel("开启音频混音入参")
public class StartAudioMixRequestDTO implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @ApiModelProperty("具体设备ID")
    private String deviceId;

    @ApiModelProperty("使用32位UUID（无横线）")
    private String groupId;

    @ApiModelProperty("混音列表")
    private List<AudioMixer> mixerList;

}
