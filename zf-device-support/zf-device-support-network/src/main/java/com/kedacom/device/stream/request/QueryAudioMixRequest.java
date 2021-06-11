package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 16:44
 */
@ToString(callSuper = true)
@Data
@ApiModel("查询混音信息业务交互参数")
public class QueryAudioMixRequest extends BaseRequest {

    private static final String COMMAND = "queryaudiomix";

    @ApiModelProperty("音频混音设备组id")
    @JSONField(name = "GroupID")
    private String groupID;

    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;

    @Override
    public String name() {
        return COMMAND;
    }


}