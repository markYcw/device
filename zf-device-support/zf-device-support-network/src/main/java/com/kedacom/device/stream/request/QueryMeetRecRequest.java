package com.kedacom.device.stream.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.core.pojo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 14:05
 */
@Data
@ApiModel(description = "查询会议录像记录入参")
public class QueryMeetRecRequest extends BaseRequest {

    private static final String COMMAND = "recmquery";

    @JSONField(name = "ChnIds")
    @ApiModelProperty(value = "通道列表")
    private String chnIds;

    @JSONField(name = "Begin")
    @ApiModelProperty(value = "开始时间time_t，时间戳")
    private Long begin;

    @JSONField(name = "End")
    @ApiModelProperty(value = "结束时间time_t，时间戳")
    private Long end;

    @JSONField(name = "StartIndex")
    @ApiModelProperty(value = "开始索引")
    private Integer startIndex;

    @JSONField(name = "PageSize")
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    @Override
    public String name() {
        return COMMAND;
    }
}
