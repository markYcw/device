package com.kedacom.mp.mcu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author hxj
 * @Date: 2021/8/12 14:51
 * @Description 会议平台基本请求参数
 */
@Data
@ApiModel(description =  "会议平台基本请求参数")
public class McuRequestDTO implements Serializable {

    @ApiModelProperty(value = "会议平台数据库id", required = true)
    @NotNull(message = "会议平台id不能为空")
    private Long mcuId;

}
