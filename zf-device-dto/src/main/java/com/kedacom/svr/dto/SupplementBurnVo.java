package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 13:34
 * @description 补刻
 */
@Data
public class SupplementBurnVo extends SvrRequestDto{

    @NotBlank(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间，如：20200827120000",required = true)
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间，如：20200827130000",required = true)
    private String endTime;

    @NotNull(message = "刻录模式不能为空")
    @ApiModelProperty(value = "刻录模式 0：无效 1：双盘同步刻录 2：只刻录DVD1(暂不支持) 3：只刻录DVD2(暂不支持) 4：循环连续刻录",required = true)
    private Integer burnMode;


}
