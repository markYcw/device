package com.kedacom.ums.responsedto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/13
 */
@Data
public class AudioVo implements Serializable {

    @ApiModelProperty(value = "设备ID")
    private String DeviceID;

    @ApiModelProperty(value = "设备类型")
    private String DeviceType;

}
