package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/8 13:16
 * @description
 */
@Data
@ApiModel(description = "获取当前语音激励状态响应")
@ToString(callSuper = true)
public class GetSvrAudioActStateResponse extends BaseResponse {

    @ApiModelProperty("激励开关，0-关，1-开")
    private Integer ActCfg;
}
