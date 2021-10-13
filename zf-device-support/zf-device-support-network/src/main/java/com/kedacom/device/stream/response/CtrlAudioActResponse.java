package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:24
 * @description
 */
@Data
@ApiModel(description = "控制音频功率上报")
@ToString(callSuper = true)
public class CtrlAudioActResponse extends BaseResponse {
}
