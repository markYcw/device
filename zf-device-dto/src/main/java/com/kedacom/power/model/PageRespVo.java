package com.kedacom.power.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 分页响应类
 */
@Data
@ApiModel(description = "分页响应")
public class PageRespVo<T> implements Serializable {

    @ApiModelProperty(name = "list", value = "列表数据")
    private T list;

    @ApiModelProperty(name = "total", value = "总数")
    private long total;

    @ApiModelProperty(name = "pageIndex", value = "当前页数")
    private long pageIndex;

    @ApiModelProperty(name = "pageSize", value = "每页条数")
    private long pageSize;

    public PageRespVo() {
    }

    public PageRespVo(T list, long total, long pageIndex, long pageSize) {
        this.list = list;
        this.total = total;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public static <T> PageRespVo<T> newInstance(T list, long total, long pageIndex, long pageSize) {
        return new PageRespVo(list, total, pageIndex, pageSize);
    }

}
