package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 16:33
 * @description
 */
@Data
public class SvrRequestDto implements Serializable {

    @ApiModelProperty("数据库ID")
    private Integer dbId;

}
