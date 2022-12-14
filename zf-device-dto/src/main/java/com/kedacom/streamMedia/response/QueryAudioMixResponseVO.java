package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.info.AudioMixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:56
 */
@Data
@ApiModel(description = "查询混音信息应答")
public class QueryAudioMixResponseVO implements Serializable {

    @ApiModelProperty("混音信息集合")
    private List<AudioMixInfo> audioMixInfo;

}
