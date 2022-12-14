package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description 获取设备组信息
 */
@ToString(callSuper = true)
@Data
public class DevGroupsDto extends CuRequestDto {

    @ApiModelProperty("设备组id")
    private String groupId;

}
