package com.kedacom.device.core.notify.cu.loadGroup.notify;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/10 11:28
 * @description
 */
@Data
public class CuNotify {

    @ApiModelProperty("ssid")
    private Integer ssid;

    @ApiModelProperty("通知类型")
    private Integer type;

}