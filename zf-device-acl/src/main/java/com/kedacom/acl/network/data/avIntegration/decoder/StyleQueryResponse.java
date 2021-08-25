package com.kedacom.acl.network.data.avIntegration.decoder;

import com.kedacom.msp.response.decoder.ChnList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/8 17:55
 */
@Data
@ApiModel(description =  "获取解码通道风格信息应答")
public class StyleQueryResponse implements Serializable {

    @ApiModelProperty("响应状态码 成功0 失败4")
    private Integer error;

    @ApiModelProperty("失败时返回信息")
    private String errstr;

    @ApiModelProperty(value = "解码通道风格信息")
    private List<ChnList> chnls;

}
