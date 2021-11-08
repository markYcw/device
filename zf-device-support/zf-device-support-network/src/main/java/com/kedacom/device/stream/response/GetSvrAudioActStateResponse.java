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

    @ApiModelProperty("通道号，目前一般为0，合成通道")
    private Integer ChnId;
}
