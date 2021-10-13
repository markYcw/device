package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/9 14:46
 * @description
 */
@Data
@ApiModel(description = "设置音频功率上报间隔")
@ToString(callSuper = true)
public class SetAudioActIntervalResponse extends BaseResponse {
}
