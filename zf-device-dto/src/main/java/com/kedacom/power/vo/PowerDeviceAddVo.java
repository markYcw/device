package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

    /**
     * 设备类型（1：RK100；2：Bwant-IPM-08）
     */
    @Min(message = "设备类型不合法", value = 1)
    @Max(message = "设备类型不合法", value = 2)
    @ApiModelProperty(value = "设备类型（1：RK100；2：Bwant-IPM-08）", required = true)
    private Integer type;

    /**
     * 电源设备名称
     */
    @NotBlank(message = "电源设备名称不能为空")
    @ApiModelProperty(value = "电源设备名称", required = true)
    private String name;

    /**
     * 设备IP地址
     */
    @NotBlank(message = "设备IP地址不能为空")
    @ApiModelProperty(value = "设备IP地址", required = true)
    private String ip;

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
