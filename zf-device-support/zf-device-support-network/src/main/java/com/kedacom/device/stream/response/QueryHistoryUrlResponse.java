package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Auther: hxj
 * @Date: 2021/5/26 14:50
 */
@Data
@ApiModel(description = "查询历史资源URL应答")
@ToString(callSuper = true)
public class QueryHistoryUrlResponse extends BaseResponse {

    @ApiModelProperty("历史资源url地址")
    private String url;

}
