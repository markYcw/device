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
 * @Date: 2021/4/30 10:44
 */
@Data
@ApiModel("更新音频混音入参")
public class UpdateAudioMixRequest implements Serializable {

    @NotBlank(message = "统一平台Id不能为空")
    @ApiModelProperty("统一平台Id，必填")
    private String unitId;

    @NotBlank(message = "混音设备分组id不能为空")
    @ApiModelProperty("混音设备分组id")
    private String GroupID;

    @NotBlank(message = "混音ID不能为空")
    @ApiModelProperty("混音ID")
    private String mixID;

    @ApiModelProperty("add-添加  update-更新  delete-删除")
    private String cmdType;

    @ApiModelProperty("参与混音方列表(最大数目为 16)")
    private List<AudioMixer> mixer_list;

}
