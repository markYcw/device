package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @ClassName PowerDeviceAddVo
 * @Description 新增电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/27 11:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "新增电源设备信息参数类")
public class PowerDeviceAddVo implements Serializable {

    @Min(message = "设备类型不合法", value = 1)
    @Max(message = "设备类型不合法", value = 2)
    @ApiModelProperty(value = "设备类型（1：RK100；2：Bwant-IPM-08）", required = true)
    private Integer type;

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
    @ApiModelProperty(value = "设备MAC地址")
    private String mac;

    @NotNull(message = "电源通道数量不能为空")
    @ApiModelProperty(value = "电源通道数量")
    private Integer channels;

    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号，RK100类型时有效", required = false)
    private Integer port;

    /**
     * RK100设备序列号
     */
    @ApiModelProperty(value = "RK100设备序列号，RK100类型时有效", required = false)
    private String deviceSn;


}
