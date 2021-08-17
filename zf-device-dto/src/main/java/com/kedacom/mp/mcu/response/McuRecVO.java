package com.kedacom.mp.mcu.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date: 2021/8/17 13:27
 * @description 开始/暂停/恢复/停止录像响应
 */
@Data
@ApiModel(value = "开始/暂停/恢复/停止录像响应")
public class McuRecVO implements Serializable {

    @ApiModelProperty(value = "录像机id，开始成功时返回")
    private String recId;

}
