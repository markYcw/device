package com.kedacom.mp.mcu.response;


import com.kedacom.mp.mcu.pojo.RecStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/29 13:27
 * @description
 */
@Data
@ApiModel(description =  "录像状态响应")
public class McuRecStatusVO {

    @ApiModelProperty(value = "录像状态")
    private RecStatus recState;


}
