package com.kedacom.device.mp.mcu.response;

import com.kedacom.device.mp.MpResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 10:49
 * @description创建/删除会议模板响应
 */
@Data
public class ConfTemplateResponse extends MpResponse {

    @ApiModelProperty(value = "会议模板ID，创建时返回")
    private String templateId;

}
