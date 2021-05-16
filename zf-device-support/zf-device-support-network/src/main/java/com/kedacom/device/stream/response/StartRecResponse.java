package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:58
 */
@Data
public class StartRecResponse extends BaseResponse {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    @ApiModelProperty("设备ID")
    private String device_id;

    @ApiModelProperty("录像ID")
    private String record_id;


}
