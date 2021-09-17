package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 11:30
 * @description 刻录任务通知内容
 */
@Data
public class BurnContent {

    @ApiModelProperty("刻录任务ID")
    private Integer burnTaskId;

    @ApiModelProperty("刻录任务状态 0：未开始 1：进行中 2：已完成 3：刻录中异常中断,刻录失败或者异常断电")
    private Integer burnState;

    @ApiModelProperty("是否新建刻录任务")
    private Integer isCreate;

    @ApiModelProperty("新建刻录任务状态 0：无效 1：正在新建刻录任务")
    private Integer createState;

}
