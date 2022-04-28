package com.kedacom.power.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/4/28 14:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@ApiModel(description = "电源配置单个通道开关操作参数类")
public class PowerDeviceTurnVO implements Serializable {

    @NotNull(message = "电源设备数据库Id不能为空")
    @ApiModelProperty(value = "电源设备数据库Id", required = true)
    private Integer id;

    @NotNull
    @Min(value = 1, message = "通道编号不合法")
    @ApiModelProperty(value = "通道编号", required = true)
    private Integer channel;

    @NotNull
    @Max(value = 1, message = "开关状态必须是0或者1")
    @Min(value = 0, message = "开关状态必须是0或者1")
    @ApiModelProperty(value = "1:表示开，0表示关", required = true)
    private Integer flag;

}
