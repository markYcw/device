package com.kedacom.device.core.notify.nm.notify;
import com.kedacom.device.core.notify.nm.pojo.NmDeviceStatusNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 8.3设备状态变更通知
 * 
 * @author ycw
 * 
 */
@Data
public class PreDeviceStatusNotify {

	@ApiModelProperty("设备状态变更通知")
	private NmDeviceStatusNotify content;

}
