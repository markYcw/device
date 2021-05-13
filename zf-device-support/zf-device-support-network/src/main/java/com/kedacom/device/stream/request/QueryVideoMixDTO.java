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
@ApiModel("查询画面合成信息业务交互参数")
public class QueryVideoMixDTO extends StreamMediaDTO {

    private static final String COMMAND = "queryvideomix";

    @ApiModelProperty("合成ID集合")
    private List<String> mixIDs;

    @Override
    String getCommand() {
        return COMMAND;
    }

}