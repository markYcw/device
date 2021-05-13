package com.kedacom.device.stream.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@Data
@ApiModel("查询所有混音业务交互参数")
public class QueryAllAudioMixDTO extends StreamMediaDTO  {

    private static final String COMMAND = "queryallaudiomix";

    @ApiModelProperty("音频混音设备组id")
    private String GroupID;

    @Override
    String getCommand() {
        return COMMAND;
    }

}
