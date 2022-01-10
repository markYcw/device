package com.kedacom.vs.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 11:00
 */
@Data
public class QueryLiveVo {

    @ApiModelProperty("数据库ID")
    private Integer dbId;

    @ApiModelProperty("查询起始，从0开始")
    private Integer start;

    private Integer count;

    @ApiModelProperty("模糊匹配的录像名字")
    private String includeName;

}
