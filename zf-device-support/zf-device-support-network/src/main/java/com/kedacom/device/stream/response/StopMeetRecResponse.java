package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author hxj
 * @date 2022/6/21 16:17
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "停止拉取媒体流应答")
public class StopMeetRecResponse extends BaseResponse {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

}
