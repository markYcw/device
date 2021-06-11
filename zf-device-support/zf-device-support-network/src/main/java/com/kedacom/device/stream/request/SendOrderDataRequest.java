package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: hxj
 * @Date: 2021/6/9 10:17
 */
@ToString(callSuper = true)
@Data
@ApiModel("发送宏指令数据交互参数")
public class SendOrderDataRequest extends BaseRequest {

    private static final String COMMAND = "sendorderdata";

    @NotBlank(message = "设备国标id不能为空")
    @JSONField(name = "DeviceID")
    private String deviceID;

    @NotBlank(message = "宏指令不能为空")
    @JSONField(name = "OrderData")
    private String orderData;

    @Override
    public String name() {
        return COMMAND;
    }

}