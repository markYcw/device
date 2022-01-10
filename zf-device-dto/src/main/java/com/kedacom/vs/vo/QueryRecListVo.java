package com.kedacom.vs.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 11:00
 */
@Data
public class QueryRecListVo {

    @ApiModelProperty("数据库ID")
    private Integer dbId;

    @ApiModelProperty("查询类型 可不填")
    private Integer type = 1;

    @ApiModelProperty("查询的页码（从1开始）")
    @JSONField(name = "start")
    private Integer pageNum;

    @ApiModelProperty("每页的大小")
    @JSONField(name = "count")
    private Integer pageSize;

    @ApiModelProperty("模糊匹配的录像名字")
    @JSONField(name = "includeName")
    private String recName;

}
