package com.kedacom.acl.network.data.streamMeida;

import com.kedacom.streamMedia.info.AudioMixer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 10:44
 */
@Data
@ApiModel("更新音频混音业务交互参数")
public class UpdateAudioMixRequestDTO implements Serializable {

    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @ApiModelProperty("混音ID")
    private String mixID;

    @ApiModelProperty("add-添加  update-更新  delete-删除")
    private String cmdType;

    @ApiModelProperty("参与混音方列表(最大数目为 16)")
    private List<AudioMixer> mixer_list;

}
