package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
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
@ApiModel("更新音频混音业务交互参数")
public class UpdateAudioMixDTO extends BaseRequest {

    private static final String COMMAND = "updateaudiomix";

    @ApiModelProperty("音频混音设备组id")
    private String GroupID;

    @ApiModelProperty("混音ID")
    private String mixID;

    @ApiModelProperty("add-添加  update-更新  delete-删除")
    private String cmdType;

    @ApiModelProperty("参与混音方列表(最大数目为 16)")
    private List<AudioMixer> mixer_list;

    @Override
    public String name() {
        return COMMAND;
    }

}