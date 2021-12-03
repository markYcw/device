package com.kedacom.mt;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/3
 */
@Data
public class MtNotifyVo implements Serializable {

    @ApiModelProperty("ssid")
    private Integer ssid;

    @ApiModelProperty("通知类型")
    private Integer type;

}
