package com.kedacom.acl.network.data.streamMeida;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 13:55
 */
@Data
@ApiModel("查询混音信息业务交互参数")
public class QueryAudioMixRequestDTO implements Serializable {

    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @ApiModelProperty("混音ID集合")
    private List<String> mixIDs;


}
