package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
@ApiModel(description = "发布直播响应参数类")
public class LiveStreamResponseVo implements Serializable {

    @ApiModelProperty("流地址，如“rtsp://xxxxx”")
    private String url;

}
