package com.kedacom.acl.network.data.avIntegration.tvwall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 19:11
 */
@Data
@ApiModel(description = "删除大屏应答")
public class TvWallDeleteResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

}
