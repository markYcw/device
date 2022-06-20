package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PowerConfigUpdateVo
 * @Description 电源设备修改配置参数类
 * @Author zlf
 * @Date 2021/5/26 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源设备修改配置参数类")
public class PowerConfigUpdateVo implements Serializable {

    /**
     * 数据库Id
     */
    @NotNull(message = "数据库Id不能为空")
    @ApiModelProperty(value = "数据库Id", required = true)
    private Integer id;

    /**
     * 设备监听端口号
     */
    @NotNull(message = "设备监听端口号不能为空")
    @ApiModelProperty(value = "设备监听端口号", required = true)
    private Integer port;

}
