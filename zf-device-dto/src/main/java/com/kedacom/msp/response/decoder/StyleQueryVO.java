package com.kedacom.msp.response.decoder;

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
public class StyleQueryVO implements Serializable {

    @ApiModelProperty(value = "解码通道风格信息")
    private List<ChnList> chnls;

}
