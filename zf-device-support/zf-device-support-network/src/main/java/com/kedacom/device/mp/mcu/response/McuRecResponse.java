package com.kedacom.device.mp.mcu.response;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hxj
 * @date: 2021/8/17 13:27
 * @description 开始/暂停/恢复/停止录像中间件响应
 */
@Data
@ApiModel(value = "开始/暂停/恢复/停止录像中间件响应")
public class McuRecResponse extends McuRequestDTO {

    @ApiModelProperty(value = "录像机id，开始成功时返回")
    private String recId;

}
