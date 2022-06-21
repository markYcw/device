package com.kedacom.streamMedia.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hxj
 * @date 2022/6/21 18:39
 */
@Data
public class QueryMeetRecTaskNode implements Serializable {

    @ApiModelProperty("录像ID")
    private String record_id;

    @ApiModelProperty("节点ID")
    private String device_id;

}
