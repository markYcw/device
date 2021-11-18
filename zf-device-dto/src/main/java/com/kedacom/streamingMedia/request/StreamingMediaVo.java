package com.kedacom.streamingMedia.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/18
 */
@Data
public class StreamingMediaVo {

    /**
     * 流媒体服务器标识
     */
    @ApiModelProperty("数据库ID")
    private Long id;
    /**
     * 流媒体服务器名称
     */
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty("流媒体服务器名称")
    private String name;
    /**
     * 流媒体服务器IP
     */
    @NotBlank(message = "ip不能为空")
    @ApiModelProperty("流媒体服务器IP")
    private String ip;
    /**
     * 流媒体服务器端口
     */
    @ApiModelProperty("流媒体服务器端口")
    private Integer port;

}
