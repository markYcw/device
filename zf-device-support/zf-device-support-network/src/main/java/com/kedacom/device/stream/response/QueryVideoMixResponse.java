package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.VideoMixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 15:05
 */
@Data
@ApiModel("查询画面合成信息应答")
@ToString(callSuper = true)
public class QueryVideoMixResponse extends BaseResponse {

    @ApiModelProperty("画面合成信息")
    private List<VideoMixInfo> videoMixInfo;

}
