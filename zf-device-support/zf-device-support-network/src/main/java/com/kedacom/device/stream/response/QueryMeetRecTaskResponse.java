package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.QueryMeetRecTaskNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date 2022/6/21 18:45
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "查询会议录像任务响应")
public class QueryMeetRecTaskResponse extends BaseResponse {

    @ApiModelProperty("唯一请求ID（用户自定义数据）")
    private String request_id;

    private List<QueryMeetRecTaskNode> nodes;
}
