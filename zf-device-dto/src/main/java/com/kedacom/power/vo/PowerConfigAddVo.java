package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PowerConfigAddVo
 * @Description 电源设备新增配置参数类
 * @Author zlf
 * @Date 2021/5/26 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源设备新增配置参数类")
public class PowerConfigAddVo implements Serializable {

    /**
     * 设备监听端口号
     */
    @NotNull(message = "设备监听端口号不能为空")
    @ApiModelProperty(value = "设备监听端口号", required = true)
    private Integer port;

}
