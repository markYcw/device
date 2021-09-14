package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 15:41
 * @description 远程点设置
 */
@Data
public class RemoteCfgVo {

    @ApiModelProperty("远程点输出主流通道")
    private Integer remotEncChn;

    @ApiModelProperty("远程点输出辅流通道")
    private Integer remoteSecChn;

    @ApiModelProperty("是否将远程点作为合成通道 0：不作为 1:作为")
    private Integer remoteMerge;

}
