package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PowerDeviceTypeResponseVo
 * @Description 电源设备类型返回参数类
 * @Author zlf
 * @Date 2021/6/10 10:11
 */
@ApiModel(description = "电源设备类型返回参数类")
@Data
@Builder
public class PowerDeviceTypeResponseVo implements Serializable {

    @ApiModelProperty(value = "电源设备类型数据库Id")
    private Integer id;

    @ApiModelProperty(value = "电源设备类型")
    private String devType;

}
