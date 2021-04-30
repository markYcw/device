package com.kedacom.streamMedia.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/29 19:34
 */
@Data
public class StoprecRequestDTO implements Serializable {

    @ApiModelProperty("录像ID")
    private  String recordId;

}
