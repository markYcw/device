package com.kedacom.svr.stragegy.svr.pojo;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 15:44
 * @description 编解码设备上报通知
 */
@Data
@ToString(callSuper = true)
public class SvrDeviceStatus extends DeviceNotifyRequestDTO{

    @ApiModelProperty("300：设备上报通知 301：刻录任务通知 302：DVD状态通知 303：语音激励通知")
    private Integer type;

    @ApiModelProperty("通知内容")
    private Content content;


}
