package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 15:13
 */
@Data
@ApiModel(description = "停止拉取媒体流应答")
@ToString(callSuper = true)
public class StopPullUrlResponse extends BaseResponse {
}
