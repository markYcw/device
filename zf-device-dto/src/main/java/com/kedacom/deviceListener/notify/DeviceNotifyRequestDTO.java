package com.kedacom.deviceListener.notify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备消息请求参数类
 * @author ycw
 * @create 2021/06/19 13:33
 */
@Data
public class DeviceNotifyRequestDTO implements Serializable {

    @ApiModelProperty("通知消息类型")
    private Integer msgType;

    @ApiModelProperty("数据库ID")
    private Integer dbId;


}
