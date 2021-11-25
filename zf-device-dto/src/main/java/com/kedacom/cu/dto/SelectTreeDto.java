package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description 选择当前操作的设备树
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SelectTreeDto extends CuRequestDto {

    @ApiModelProperty("设备树id")
    @NotBlank
    private String id;

}
