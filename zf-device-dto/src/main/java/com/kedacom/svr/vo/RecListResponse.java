package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 15:59
 * @description
 */
@Data
public class RecListResponse {

    @ApiModelProperty("本次查询总数")
    private int total;

    @ApiModelProperty("录像列表")
    private List<RecVo> recList;
}
