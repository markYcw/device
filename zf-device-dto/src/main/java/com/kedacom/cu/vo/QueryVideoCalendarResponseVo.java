package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/15
 */
@Data
@ApiModel(description = "查询录像日历响应参数类")
public class QueryVideoCalendarResponseVo implements Serializable {

    @ApiModelProperty("所选日期当天是否有录像, 1 : 有; 0 : 没有")
    private List<Integer> recDays;

}
