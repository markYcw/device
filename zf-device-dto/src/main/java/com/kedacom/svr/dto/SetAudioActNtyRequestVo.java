package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 9:27
 * @description 语音激励控制
 */
@Data
public class SetAudioActNtyRequestVo extends SvrRequestDto{

    @ApiModelProperty("0：开启 1：停止")
    private Integer type;

}
