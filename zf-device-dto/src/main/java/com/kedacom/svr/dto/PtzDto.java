package com.kedacom.svr.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 13:35
 * @description ptz控制
 */
@Data
public class PtzDto extends SvrRequestDto{

    @ApiModelProperty("通道ID")
    private Integer chnId;

    @ApiModelProperty("控制命令 控制指令解释请参考PtzCmd枚举类")
    private Integer cmd;

    @ApiModelProperty("控制指令解释PtzCmd枚举类，此字段为上一个字段起解释说明服务，不比传")
    private PtzCmd ptzCmd;

    @ApiModelProperty("命令参数 请参考PtzCmd枚举类")
    private String param1;

    @ApiModelProperty("命令参数 请参考PtzCmd枚举类")
    private String param2;

    @ApiModelProperty("命令参数 请参考PtzCmd枚举类")
    private String param3;

    @ApiModelProperty("命令参数 请参考PtzCmd枚举类")
    private String param4;

}
