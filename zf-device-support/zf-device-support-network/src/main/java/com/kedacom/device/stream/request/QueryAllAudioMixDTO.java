package com.kedacom.device.stream.request;

import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
@ApiModel("查询所有混音业务交互参数")
public class QueryAllAudioMixDTO extends BaseRequest {

    private static final String COMMAND = "queryallaudiomix";

    @ApiModelProperty("音频混音设备组id")
    private String GroupID;

    @Override
    public String name() {
        return COMMAND;
    }

}
