package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description
 */
@Data
public class SvrRequestDto implements Serializable {

    @NotNull(message = "数据库ID不能为空")
    @ApiModelProperty(value = "数据库ID",required = true)
    private Integer dbId;

}
