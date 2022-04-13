package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @ClassName PowerDeviceUpdateVo
 * @Description 修改电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/27 11:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "修改电源设备信息参数类")
public class PowerDeviceUpdateVo implements Serializable {

    /**
     * 电源设备数据库Id
     */
    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;

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
    @ApiModelProperty(value = "电源设备名称", required = false)
    private String name;

    /**
     * 设备IP地址
     */
    @ApiModelProperty(value = "设备IP地址", required = false)
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
