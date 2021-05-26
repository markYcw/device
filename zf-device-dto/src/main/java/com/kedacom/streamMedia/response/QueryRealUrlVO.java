package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/26 14:50
 */
@Data
@ApiModel("查询实时资源URL应答")
public class QueryRealUrlVO implements Serializable {

    @ApiModelProperty("实时资源url地址")
    private String url;

}
