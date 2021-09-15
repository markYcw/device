package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 11:15
 * @description 刻录控制
 */
@Data
public class BurnDto extends SvrRequestDto{

    @ApiModelProperty("0：开始刻录 1：暂停刻录 2：恢复刻录 3：停止刻录 4：中断刻录")
    private Integer type;

    @ApiModelProperty("刻录任务ID")
    private Integer burnTaskId;

    @ApiModelProperty("刻录模式 0：双盘同步 1：循环刻录")
    private Integer burnMode;

    @ApiModelProperty("工作模式： 0：实时刻录 1：只录像不刻录")
    private Integer workMode;

}
