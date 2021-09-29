package com.kedacom.mp.mcu.request;

import com.kedacom.mp.mcu.McuRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/29 13:27
 * @description
 */
@Data
public class McuRecStatusDTO extends McuRequestDTO {

    @NotBlank(message = "会议号码不能为空")
    @ApiModelProperty(value = "会议号码", required = true)
    private String confId;

    @ApiModelProperty(value = "录像id，非开始时必填")
    private String recId;

}
