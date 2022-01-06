package com.kedacom.mt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/1
 */
@Data
@ApiModel(description = "开启点对点会议请求参数类")
public class StartMeetingMtVo implements Serializable {

    @ApiModelProperty(value = "数据库ID", required = true)
    private Integer dbId;

    @ApiModelProperty(value = "对端设备信息类型 key : 0:ip 1:e164 2:h323 3:id", required = true)
    private Integer key;

    @ApiModelProperty(value = "对端设备信息值 value : 具体 key 对应的值", required = true)
    private String value;

    @ApiModelProperty(value = "呼叫方式 : 0:ip 1:e164 2:h323id", required = true)
    private Integer addrType;

    @ApiModelProperty(value = "呼叫类型： 0：呼叫SVR 1：呼叫终端", required = true)
    private Integer callType;

    @ApiModelProperty(value = "呼叫码率, 512/768/1024/4096", required = true)
    private Integer bitrate;

}
