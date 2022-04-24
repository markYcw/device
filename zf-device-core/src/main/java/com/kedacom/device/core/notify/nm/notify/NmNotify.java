package com.kedacom.device.core.notify.nm.notify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:28
 * @description
 */
@Data
public class NmNotify {

    @ApiModelProperty("会话号")
    private Integer ssid;

    @ApiModelProperty("流水号")
    private Integer ssno;

    @ApiModelProperty("通知类型")
    private Integer type;

}
