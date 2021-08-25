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
 * @Date: 2021/4/30 10:44
 */
@Data
@ApiModel(description = "更新音频混音入参")
public class UpdateAudioMixDTO implements Serializable {

    @NotBlank(message = "umsId不能为空")
    @ApiModelProperty(value = "平台id")
    private String umsId;

    @NotBlank(message = "混音设备分组id不能为空")
    @ApiModelProperty("混音设备分组id")
    private String groupID;

    @NotBlank(message = "混音ID不能为空")
    @ApiModelProperty("混音ID")
    private String mixID;

    @ApiModelProperty("add-添加  update-更新  delete-删除")
    @NotBlank(message = "操作类型不能为空")
    private String cmdType;

    @ApiModelProperty("参与混音方列表(最大数目为16)")
    @NotEmpty(message = "参与混音列表不能为空")
    private List<AudioMixer> mixer_list;

}
