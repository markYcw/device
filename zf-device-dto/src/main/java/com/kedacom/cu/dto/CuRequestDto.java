package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description 监控平台请求体基类
 */
@Data
public class CuRequestDto implements Serializable {

    @ApiModelProperty("数据库ID")
    @NotNull
    private Integer dbId;

}
