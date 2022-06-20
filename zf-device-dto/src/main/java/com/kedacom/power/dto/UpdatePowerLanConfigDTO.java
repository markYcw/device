package com.kedacom.power.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author hexijian
 * @date 2022/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "局域网添加-新增电源设备信息参数类")
public class UpdatePowerLanConfigDTO implements Serializable {

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
    @ApiModelProperty(value = "设备MAC地址")
    private String mac;

    @NotBlank(message = "网关不能为空")
    @Pattern(regexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$",
            message = "网关IP地址格式错误")
    @ApiModelProperty(value = "网关(改成服务器段)")
    private String devGatewayIp;

    @NotBlank(message = "子网掩码不能为空")
    @Pattern(regexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$",
            message = "子网掩码格式错误")
    @ApiModelProperty(value = "子网掩码(改成服务器段)")
    private String devIpMask;

    @NotBlank(message = "服务器ip不能为空")
    @Pattern(regexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$",
            message = "服务器ip格式错误")
    @ApiModelProperty(value = "服务器ip")
    private String desIp;

    @NotNull(message = "服务器端口不能为空")
    @ApiModelProperty(value = "服务器端口")
    private Integer desPort;

}
