package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import com.kedacom.streamMedia.info.MeetRecords;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author hxj
 * @date 2022/6/21 14:41
 */
@Data
@ToString(callSuper = true)
@ApiModel(description = "查询会议录像记录响应")
public class QueryMeetRecResponse extends BaseResponse {

    @ApiModelProperty(value = "本次消息返回的记录数量")
    private Integer ResultNum;

    @ApiModelProperty(value = "是否返回全部结果，1-返回全部结果，0-返回部分结果")
    private Integer Finished;

    @ApiModelProperty(value = "es中所有录像计划数量")
    private Integer TotalNum;

    @ApiModelProperty(value = "返回的录像记录结果集")
    private List<MeetRecords> Records;

}
