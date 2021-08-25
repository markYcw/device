package com.kedacom.msp.request.precall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 15:32
 */
@Data
@ApiModel(description = "新建预加载任务入参")
public class PrecallAddRequest implements Serializable {

    @NotEmpty
    @ApiModelProperty("任务名称")
    private String plan_name;

    @ApiModelProperty("起始时间，格式YYYY-MM-DDThh:mm:ss")
    private String start_time;

    @ApiModelProperty("结束时间，格式YYYY-MM-DDThh:mm:ss")
    private String end_time;

    @NotEmpty
    @ApiModelProperty("信号源ID列表")
    private List<String> deviceIDs;

}
