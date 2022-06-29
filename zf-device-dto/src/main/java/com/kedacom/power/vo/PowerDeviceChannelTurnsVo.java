package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName PowerDeviceChannelTurnsVo
 * @Description 电源配置多个通道具体开关操作参数类
 * @Author zlf
 * @Date 2021/5/25 13:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源配置多个通道具体开关操作参数类")
public class PowerDeviceChannelTurnsVo implements Serializable {

    @NotNull(message = "通道编号不能为空")
    @Min(value = 1, message = "通道编号不合法")
    @ApiModelProperty(value = "通道编号", required = true)
    private Integer channel;

    @NotNull(message = "通道开关状态不能为空")
    @Max(value = 1, message = "开关状态必须是0或者1")
    @Min(value = 0, message = "开关状态必须是0或者1")
    @ApiModelProperty(value = "1:表示开，0表示关", required = true)
    private Integer flag;

}
