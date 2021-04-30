package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.data.AudioMixInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:56
 */
@Data
public class QueryAudioMixResponseVO implements Serializable {

    @ApiModelProperty("混音ID集合")
    private List<AudioMixInfo> audioMixInfoList;

}
