package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.CapacityAlarmUrl;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date 2022/6/22 10:14
 */
@Data
@ToString(callSuper = true)
public class QueryMeetRecordCapResponse extends BaseResponse {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String requestId;

    @ApiModelProperty(value = "int64格式，总空间大小，单位字节")
    private Long totalSize;

    @ApiModelProperty(value = "int64格式，剩余空间大小，单位字节")
    private Long freeSize;

}
