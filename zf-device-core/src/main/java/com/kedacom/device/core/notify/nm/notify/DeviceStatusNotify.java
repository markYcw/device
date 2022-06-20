package com.kedacom.device.core.notify.nm.notify;

import com.kedacom.device.core.notify.cu.loadGroup.notify.CuNotify;
import com.kedacom.device.core.notify.cu.loadGroup.pojo.GetDeviceStatusNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:27
 * @description cu设备通知
 */
@ToString(callSuper = true)
@Data
public class DeviceStatusNotify extends CuNotify {

    @ApiModelProperty("设备通知内容")
    private GetDeviceStatusNotify content;

}
