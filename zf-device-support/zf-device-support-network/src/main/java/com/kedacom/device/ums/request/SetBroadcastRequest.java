package com.kedacom.device.ums.request;

import com.kedacom.core.pojo.BaseRequest;
import com.kedacom.ums.responsedto.MediaVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/14
 */
@Data
@ToString(callSuper = true)
@ApiModel(description =  "设置调度组广播源")
public class SetBroadcastRequest extends BaseRequest {

    private static final String COMMAND = "setbroadcast";

    @ApiModelProperty(value = "调度组Id")
    private String GroupID;

    @ApiModelProperty(value = "广播源列表包括音频和视频")
    private MediaVo Broadcast;

    @Override
    public String name() {
        return COMMAND;
    }

}
