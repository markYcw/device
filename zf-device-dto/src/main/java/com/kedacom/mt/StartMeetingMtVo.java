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

    @ApiModelProperty(value = "0:ip 1:e164 2:h323id 3:dialnum 4:sipaddr", required = true)
    private Integer addrType;

    @ApiModelProperty(value = "对端数据库ID", required = true)
    private Integer dstId;

    @ApiModelProperty("别名（可以不传）")
    private String alias;

    @ApiModelProperty(value = "呼叫码率, 512/768/1024/4096", required = true)
    private Integer bitrate;

    @ApiModelProperty(value = "呼叫类型： 0：呼叫SVR 1：呼叫终端", required = true)
    private Integer callType;

}
