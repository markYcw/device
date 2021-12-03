package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 11:15
 * @description 刻录控制
 */
@Data
public class BurnDto extends SvrRequestDto{

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：开始刻录 1：暂停刻录 2：恢复刻录 3：停止刻录 4：中断刻录",required = true)
    private Integer type;

    @NotNull(message = "刻录任务ID不能为空")
    @ApiModelProperty(value = "刻录任务ID 补刻开始时可以填0",required = true)
    private Integer burnTaskId;

    @ApiModelProperty("刻录模式 0：无效 1：双盘同步刻录 2：只刻录DVD1(暂不支持) 3：只刻录DVD2(暂不支持) 4：循环连续刻录")
    private Integer burnMode;

    @ApiModelProperty("工作模式： 0：实时刻录 1：补刻 2：只录像不刻录")
    private Integer workMode;

}
