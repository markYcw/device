package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:47
 */
@ToString(callSuper = true)
@Data
public class QueryAllVideoMixRequest extends BaseRequest {

    private static final String COMMAND = "queryallvideomix";

    @ApiModelProperty("画面合成设备分组id")
    @JSONField(name = "GroupID")
    private String groupID;

    @Override
    public String name() {
        return COMMAND;
    }

}
