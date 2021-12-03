package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 10:33
 * @description 修改远程点配置
 */
@Data
public class RemotePutCfgDto extends SvrRequestDto{

    @NotNull(message = "远程点输出主流通道不能为空")
    @ApiModelProperty(value = "远程点输出主流通道",required = true)
    private Integer remotEncChn;

    @NotNull(message = "远程点输出辅流通道不能为空")
    @ApiModelProperty(value = "远程点输出辅流通道",required = true)
    private Integer remoteSecChn;

    @NotNull(message = "是否将远程点作为合成通道不能为空")
    @ApiModelProperty(value = "是否将远程点作为合成通道： 0：不作为 1：作为",required = true)
    private Integer remoteMerge;

}
