package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PowerDeviceTurnsVo
 * @Description 电源配置多个通道开关操作请求参数类
 * @Author zlf
 * @Date 2021/5/25 13:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源配置多个通道开关操作参数类")
public class PowerDeviceTurnsVo implements Serializable {

    @NotNull(message = "电源设备数据库Id不能为空")
    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;

    @Valid
    @NotEmpty(message = "通道开关操作对应集合不能为空")
    @ApiModelProperty(required = true)
    private List<PowerDeviceChannelTurnsVo> channels;

}
