package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 10:33
 * @description 修改远程点配置
 */
@Data
public class RemotePutCfgDto extends SvrRequestDto{

    @ApiModelProperty("远程点输出主流通道")
    private Integer remotEncChn;

    @ApiModelProperty("远程点输出辅流通道")
    private Integer remoteSecChn;

    @ApiModelProperty("是否将远程点作为合成通道： 0：不作为 1：作为")
    private Integer remoteMerge;

}
