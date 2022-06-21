package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author  hxj
 * @date  2021/4/29 18:45
 */
@Data
@ApiModel(description = "开启录像应答")
public class StartRecMeetResponseVO implements Serializable {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("设备ID")
    private String device_id;

    @ApiModelProperty("录像ID")
    private String record_id;

}
