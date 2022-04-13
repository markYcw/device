package com.kedacom.power.vo;

import com.kedacom.power.model.PageReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceListVo
 * @Description 条件查询电源设备信息参数类
 * @Author zlf
 * @Date 2021/5/27 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "条件查询电源设备信息参数类")
public class PowerDeviceListVo extends PageReqVo implements Serializable {

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

}
