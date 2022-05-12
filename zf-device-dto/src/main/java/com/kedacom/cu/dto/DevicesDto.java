package com.kedacom.cu.dto;

import com.kedacom.cu.pojo.Subscribe;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description 获取设备组信息
 */
@ToString(callSuper = true)
@Data
public class DevicesDto extends CuRequestDto {

    @ApiModelProperty("设备组id")
    @NotBlank(message = "设备组id不能为空")
    private String groupId;

    @ApiModelProperty("订阅信息，不需要立刻订阅可不填")
    @Valid
    private Subscribe subscribe;

    @ApiModelProperty("设备组id")
    private Integer needPuId10;

    @ApiModelProperty("设备组id")
    private Integer needGbId;

}
