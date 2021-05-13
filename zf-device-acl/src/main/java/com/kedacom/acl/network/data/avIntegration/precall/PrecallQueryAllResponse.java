package com.kedacom.acl.network.data.avIntegration.precall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 11:10
 */
@Data
@ApiModel("获取预加载任务应答")
public class PrecallQueryAllResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败-1")
    private Integer error;

    @ApiModelProperty("任务ID列表")
    private List<Integer> preCallIDs;

    @ApiModelProperty("失败时信息")
    private String message;

}
