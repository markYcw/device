package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.streamMedia.request.RemDev;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/6/9 10:17
 */
@ToString(callSuper = true)
@Data
@ApiModel(description = "发送宏指令数据交互参数")
public class SendOrderDataRequest extends BaseRequest {

    private static final String COMMAND = "sendorderdata";

    @ApiModelProperty(value = "设备国标id", required = true)
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty(value = "宏指令", required = true)
    @JSONField(name = "OrderData")
    private String orderData;

    @ApiModelProperty(value = "宏指令类型,0:常规;1：添加启用远程点(此时需要传入RemDev参数)")
    @JSONField(name = "Type")
    private Integer type;

    @ApiModelProperty(value = "远程点设备信息")
    @JSONField(name = "RemDev")
    private RemDev remDev;

    @Override
    public String name() {
        return COMMAND;
    }

}