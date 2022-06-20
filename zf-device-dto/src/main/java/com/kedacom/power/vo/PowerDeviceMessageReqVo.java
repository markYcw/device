package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PowerDeviceMessageReqVo
 * @Description 电源设备数据库Id参数类
 * @Author zlf
 * @Date 2021/5/25 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源设备数据库Id参数类")
public class PowerDeviceMessageReqVo implements Serializable {

    @NotNull(message = "电源设备数据库Id不能为空")
    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;
}
