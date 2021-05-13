package com.kedacom.device.stream.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/13 19:27
 */
public class QueryAllVedioMixResponse extends StreamMediaResponse {

    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;

}