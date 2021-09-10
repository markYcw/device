package com.kedacom.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 14:57
 * @description 远程点
 */
@Data
public class RemotePoint {

    @ApiModelProperty("远程点名称")
    private String name;

    @ApiModelProperty("远程点url")
    private String url;

}
