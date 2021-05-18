package com.kedacom.device.stream.response;

import com.kedacom.core.pojo.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 19:27
 */
@Data
@ToString(callSuper = true)
public class QueryAllVedioMixResponse extends BaseResponse {

    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;

}