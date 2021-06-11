package com.kedacom.streamMedia.request;

import com.kedacom.streamMedia.info.AudioMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 09:35
 */
@Data
@ApiModel("开启音频混音入参")
public class StartAudioMixDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @ApiModelProperty("参与混音方列表(最大数目为16)")
    @NotEmpty(message = "混音列表不能为空")
    private List<AudioMixer> mixer_list;

}
