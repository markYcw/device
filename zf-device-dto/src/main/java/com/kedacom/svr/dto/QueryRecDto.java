package com.kedacom.svr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 16:01
 * @description 查询录像
 */
@Data
public class QueryRecDto extends SvrRequestDto{

    @NotNull(message = "通道ID不能为空")
    @ApiModelProperty(value = "通道ID",required = true)
    @JSONField(name = "chnId")
    private Integer chnid;

    @NotBlank(message = "开始时间不能为空")
    @ApiModelProperty(value = "开始时间，如：20200827120000",required = true)
    @JSONField(name = "startTime")
    private String starttime;

    @NotBlank(message = "结束时间不能为空")
    @ApiModelProperty(value = "结束时间，如：20200827130000",required = true)
    @JSONField(name = "endTime")
    private String endtime;

    @NotNull(message = "查询起始索引不能为空")
    @ApiModelProperty(value = "查询起始索引",required = true)
    private Integer queryIndex;

    @NotNull(message = "查询总数不能为空")
    @ApiModelProperty(value = "查询总数，最大值为16",required = true)
    private Integer queryCount;

}
