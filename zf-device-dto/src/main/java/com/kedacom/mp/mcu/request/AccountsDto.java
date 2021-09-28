package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/28 9:47
 * @description
 */
@Data
@ApiModel(description =  "查询所有账户")
public class AccountsDto extends McuRequestDTO {

    @NotNull(message = "获取的起始位置不能为空")
    @ApiModelProperty(value = "获取的起始位置, 0表示第一个, 默认为0", required = true)
    private Integer start;

    @NotNull(message = "获取的个数不能为空")
    @ApiModelProperty(value = "获取的个数, 即包括start在内的后count个, 0代表获取所有,", required = true)
    private Integer count;


}
