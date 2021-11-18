package com.kedacom.streamingMedia.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 流媒体服务器分页查询载体
 * @author ycw
 * @create 2020/12/29 13:20
 */
@Data
@ApiModel(description = "分页列表请求参数")
public class StreamingMediaQueryDto implements Serializable {

    @ApiModelProperty("第几页")
    private int page;

    @ApiModelProperty("条数")
    private int limit;

}
