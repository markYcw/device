package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/6/9 10:20
 */
@Data
@ApiModel("发送宏指令数据应答信息")
@ToString(callSuper = true)
public class SendOrderDataResponse extends BaseResponse {


}

