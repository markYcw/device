package com.kedacom.device.core.notify.nm.notify;
import com.kedacom.device.core.notify.nm.pojo.NmGroupDeviceChangeNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:27
 * @description 分组设备对应关系变更通知通知
 */
@ToString(callSuper = true)
@Data
public class PreGroupsDeviceChangeNotify extends NmNotify {

    @ApiModelProperty("分组通知内容")
    private NmGroupDeviceChangeNotify content;

}
