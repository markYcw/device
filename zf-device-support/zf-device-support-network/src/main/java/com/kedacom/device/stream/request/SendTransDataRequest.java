package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/20 15:37
 */
@ToString(callSuper = true)
@Data
@ApiModel(description = "发送透明通道数据交互参数")
public class SendTransDataRequest extends BaseRequest {

    private static final String COMMAND = "sendtransdata";

    @ApiModelProperty(value = "设备ID")
    @JSONField(name = "DeviceID")
    private String deviceID;

    @ApiModelProperty(value = "是否是透明数据扩展,默认值:0,0:不是、1:是")
    @JSONField(name = "Ext")
    private Integer ext;

    @ApiModelProperty(value = "控制类型。当Ext为1时AppCmd不起作用")
    @JSONField(name = "AppCmd")
    private Integer appCmd;

    @ApiModelProperty(value = "透明数据")
    @JSONField(name = "TransData")
    private String transData;

    @Override
    public String name() {
        return COMMAND;
    }

}