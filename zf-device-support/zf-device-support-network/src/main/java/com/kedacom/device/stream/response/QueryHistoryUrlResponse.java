package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: hxj
 * @Date: 2021/5/26 14:50
 */
@Data
@ApiModel("查询历史资源URL应答")
public class QueryHistoryUrlResponse extends BaseResponse {

    @ApiModelProperty("历史资源url地址")
    private String url;

}
