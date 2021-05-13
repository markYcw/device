package com.kedacom.device.stream.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@Data
@ApiModel("查询混音信息业务交互参数")
public class QueryAudioMixDTO extends StreamMediaDTO {

    private static final String COMMAND = "queryaudiomix";

    @ApiModelProperty("音频混音设备组id")
    private String GroupID;

    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;

    @Override
    String getCommand() {
        return COMMAND;
    }


}