package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/18 14:51
 * @description 合成画面保活中间件请求参数
 */
@Data
@ApiModel(value = "合成画面保活中间件请求参数")
public class VideoMixKeepAliveRequest extends BaseRequest {

    private static final String COMMAND = "videomixkeepalive";

    @ApiModelProperty("画面合成设备分组id")
    @JSONField(name = "GroupID")
    private String groupID;

    @ApiModelProperty("合成ID集合")
    private List<String> mixIDs;

    @Override
    public String name() {
        return COMMAND;
    }

}
