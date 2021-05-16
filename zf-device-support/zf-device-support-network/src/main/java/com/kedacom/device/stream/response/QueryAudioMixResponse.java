package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.AudioMixInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:56
 */
@Data
@ApiModel("查询混音信息应答")
public class QueryAudioMixResponse extends BaseResponse {

    @ApiModelProperty("混音信息集合")
    private List<AudioMixInfo> audioMixInfo;

}
