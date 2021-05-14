package com.kedacom.device.stream.response;

import com.kedacom.streamMedia.info.VideoMixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 15:05
 */
@Data
@ApiModel("查询画面合成信息应答")
public class QueryVideoMixResponse extends StreamMediaResponse {

    @ApiModelProperty("画面合成信息")
    private List<VideoMixInfo> videoMixInfo;

}