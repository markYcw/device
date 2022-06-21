package com.kedacom.streamMedia.response;

import com.kedacom.streamMedia.info.MeetRecords;
import com.kedacom.streamMedia.info.MeetRecordsLowerCase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date 2022/6/21 14:41
 */
@Data
@ApiModel(description = "查询会议录像记录响应")
public class QueryMeetRecVO implements Serializable {

    @ApiModelProperty(value = "本次消息返回的记录数量")
    private Integer resultNum;

    @ApiModelProperty(value = "是否返回全部结果，1-返回全部结果，0-返回部分结果")
    private Integer finished;

    @ApiModelProperty(value = "es中所有录像计划数量")
    private Integer totalNum;

    @ApiModelProperty(value = "返回的录像记录结果集")
    private List<MeetRecordsLowerCase> records;

}
