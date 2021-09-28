package com.kedacom.mp.mcu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/26 10:40
 * @description数据协作
 */
@Data
@ApiModel(description =  "数据协作")
public class Dcs {

    @NotNull(message = "数据协作模式 不能为空")
    @ApiModelProperty(value = "数据协作模式 0-关闭数据协作； 1-管理方控制； 2-自由协作；",required = true)
    private Integer dualStream;

}
