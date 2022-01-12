package com.kedacom.vs.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 11:00
 */
@Data
public class QueryRecVo {

    @ApiModelProperty("数据库ID")
    private Integer dbId;

    @ApiModelProperty("查询类型 0：rtsp 1：http（5.0不支持）")
    private Integer type;

    @ApiModelProperty("查询起始，从0开始")
    private Integer start;

    @ApiModelProperty("查询总数")
    private Integer count;

    @ApiModelProperty("模糊匹配的录像名字")
    private String includeName;

}
