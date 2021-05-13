package com.kedacom.acl.network.data.streamMeida;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:51
 */
@Data
@ApiModel("查询所有混音业务交互参数")
public class QueryAllAudioMixRequestDTO implements Serializable {

    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

}
