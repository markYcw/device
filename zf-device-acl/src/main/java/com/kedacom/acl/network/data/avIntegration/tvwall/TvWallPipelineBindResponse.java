package com.kedacom.acl.network.data.avIntegration.tvwall;

import com.kedacom.msp.info.BindInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/6 14:53
 */
@Data
@ApiModel("配置大屏通道绑定应答")
public class TvWallPipelineBindResponse implements Serializable {

    @ApiModelProperty("响应状态码:成功0(成功只返回状态码，其余属性均为空)、失败4")
    private Integer error;

    @ApiModelProperty(value = "大屏ID")
    private Integer tvid;

    @ApiModelProperty(value = "大屏个数")
    private Integer number;

    @ApiModelProperty(value = "解码器")
    private List<BindInfo> bindinfo;
}
