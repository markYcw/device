package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:20
 * @description 创建刻录任务
 */
@Data
public class CreateBurnRequestVo extends SvrRequestDto{

    @ApiModelProperty(value = "开始时间，如：例如：2021-09-09T09:30:29.0000",required = true)
    private Date startTime;

    @ApiModelProperty(value = "结束时间，如：例如：2021-09-09T09:30:29.0000",required = true)
    private Date endTime;

}
