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
@ApiModel("停止画面合成业务交互参数")
public class StopVideoMixDTO extends StreamMediaDTO {

    private static final String COMMAND = "stopvideomix";

    @ApiModelProperty("画面合成设备分组id")
    private String GroupID;

    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

    @Override
    String getCommand() {
        return COMMAND;
    }

}
