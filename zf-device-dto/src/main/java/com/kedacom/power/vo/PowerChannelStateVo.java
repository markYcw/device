package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerChannelStateVo
 * @Description 设备通道对应状态参数类
 * @Author zlf
 * @Date 2021/5/25 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "设备通道对应状态参数类")
public class PowerChannelStateVo implements Serializable {

    @ApiModelProperty(value = "通道编号")
    private int channel;

    @ApiModelProperty(value = "状态（1：开；0：关）")
    private int state;

}
