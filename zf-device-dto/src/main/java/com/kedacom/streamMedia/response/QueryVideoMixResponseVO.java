package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.data.AudioMixInfo;
import com.kedacom.streamMedia.data.VideoMixInfo;
import com.kedacom.streamMedia.data.VideoMixer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 15:05
 */
@Data
public class QueryVideoMixResponseVO implements Serializable {

    @ApiModelProperty("画面合成信息")
    private List<VideoMixInfo> videoMixInfoList;

}
