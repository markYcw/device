package com.kedacom.device.core.notify.nm.notify;
import com.kedacom.device.core.notify.nm.pojo.NmGetGroupNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:27
 * @description cu分组通知
 */
@ToString(callSuper = true)
@Data
public class PreGroupsNotify extends NmNotify {

    @ApiModelProperty("分组通知内容")
    private NmGetGroupNotify content;

}
