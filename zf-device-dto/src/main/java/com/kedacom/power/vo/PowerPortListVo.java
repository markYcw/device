package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @ClassName PowerPortListVo
 * @Description 电源配置监听端口参数类
 * @Author zlf
 * @Date 2021/5/26 11:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源配置监听端口参数类")
public class PowerPortListVo implements Serializable {

    /**
     * 数据库Id
     */
    @ApiModelProperty(value = "数据库Id")
    private Integer id;

    /**
     * 设备监听端口号
     */
    @ApiModelProperty(value = "设备监听端口号")
    private Integer port;
}
