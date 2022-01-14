package com.kedacom.svr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/15 9:27
 * @description 语音激励控制
 */
@Data
public class SetAudioActNtyRequestVo extends SvrRequestDto{

    @NotNull(message = "type不能为空")
    @ApiModelProperty(value = "0：开启 1：停止",required = true)
    @JSONField(name ="type")
    private Integer isNty;

}
