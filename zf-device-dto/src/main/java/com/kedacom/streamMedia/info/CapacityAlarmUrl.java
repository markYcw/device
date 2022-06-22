package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/22 10:06
 */
@Data
public class CapacityAlarmUrl implements Serializable {

    @ApiModelProperty(value = "告警url")
    private String url;

}
