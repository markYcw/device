package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:09
 * @description 语音激励通知
 */
@Data
public class AudioAct {

    @ApiModelProperty("300：设备上报通知 301：刻录任务通知 302：DVD状态通知 303：语音激励通知")
    private Integer type;

    @ApiModelProperty("通知内容")
    private BurnContent content;
}
