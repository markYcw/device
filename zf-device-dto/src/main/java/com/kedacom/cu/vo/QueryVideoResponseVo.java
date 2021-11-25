package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/15
 */
@Data
@ApiModel(description = "查询录像响应参数类")
public class QueryVideoResponseVo implements Serializable {

    @ApiModelProperty("录像总数")
    private Integer num;

}
