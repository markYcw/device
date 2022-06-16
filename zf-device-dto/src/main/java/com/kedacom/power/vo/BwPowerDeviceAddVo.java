package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "新增电源设备信息参数类")
public class BwPowerDeviceAddVo implements Serializable {

    @NotBlank(message = "电源设备名称不能为空")
    @ApiModelProperty(value = "电源设备名称", required = true)
    private String name;

    @NotBlank(message = "设备IP地址不能为空")
    @Pattern(regexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$",
            message = "设备IP地址格式错误")
    @ApiModelProperty(value = "设备IP地址", required = true)
    private String ip;

    @NotBlank(message = "设备MAC地址不能为空")
    @Pattern(regexp = "^[a-fA-F0-9]{2}(-[a-fA-F0-9]{2}){5}$",
            message = "设备MAC地址格式错误")
    @ApiModelProperty(value = "设备MAC地址", required = true)
    private String mac;

    @NotNull(message = "电源通道数量不能为空")
    @ApiModelProperty(value = "电源通道数量", required = true)
    private Integer channels;

}
