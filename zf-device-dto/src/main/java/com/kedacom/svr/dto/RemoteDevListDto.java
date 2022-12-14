package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:01
 * @description 查询录像
 */
@Data
public class RemoteDevListDto extends SvrRequestDto{

    @NotNull(message = "查询起始索引不能为空")
    @ApiModelProperty(value = "查询起始索引，从0开始",required = true)
    private Integer queryIndex;

    @NotNull(message = "查询总数不能为空")
    @ApiModelProperty(value = "查询总数，最大值为16",required = true)
    private Integer queryCount;

}
