package com.kedacom.device.stream.request;

import com.kedacom.streamMedia.info.AudioMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
@ApiModel("开启音频混音业务交互参数")
public class StartAudioMixDTO extends StreamMediaDTO  {

    private static final String COMMAND = "startaudiomix";

    @ApiModelProperty("混音设备分组id")
    private String GroupID;

    @ApiModelProperty("混音列表")
    private List<AudioMixer> mixer_list;

    @Override
    public String getCommand() {
        return null;
    }

}
