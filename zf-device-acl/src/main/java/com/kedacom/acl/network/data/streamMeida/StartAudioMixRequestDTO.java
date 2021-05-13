package com.kedacom.acl.network.data.streamMeida;

import com.kedacom.streamMedia.info.AudioMixer;
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
@ApiModel("开启音频混音业务交互参数")
public class StartAudioMixRequestDTO implements Serializable {

    @NotBlank(message = "使用32位UUID不能为空")
    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @ApiModelProperty("混音列表")
    private List<AudioMixer> mixer_list;

}
