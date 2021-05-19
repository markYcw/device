package com.kedacom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/5/8
 */
@Data
public class BasePage<T> {

    @ApiModelProperty("当前页码")
    @Min(value = 1L, message = "页码数不合法")
    private Integer curPage = 0;

    @ApiModelProperty("每页多少")
    @Min(value = 1L, message = "每页条数不合法")
    private Integer pageSize = 10;

    @ApiModelProperty("总页数")
    private Long totalPage;

    @ApiModelProperty("总数目")
    private Long total;

    private List<T> data;

}
