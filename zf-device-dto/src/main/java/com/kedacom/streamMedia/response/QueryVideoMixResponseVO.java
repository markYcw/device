package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.info.VideoMixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 15:05
 */
@Data
@ApiModel(description = "查询画面合成信息应答")
public class QueryVideoMixResponseVO implements Serializable {

    @ApiModelProperty("画面合成信息")
    private List<VideoMixInfo> videoMixInfo;

}
