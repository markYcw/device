package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 14:12
 */
@Data
@ApiModel("开始推送媒体流应答")
@ToString(callSuper = true)
public class StartPushUrlResponse extends BaseResponse {

    private Integer sessionID;

}
