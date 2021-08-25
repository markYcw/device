package com.kedacom.acl.network.data.streamMeida;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 15:03
 */
@Data
@ApiModel(description = "查询画面合成信息业务交互参数")
public class QueryVideoMixRequestDTO implements Serializable {

    @ApiModelProperty("合成ID集合")
    private List<String> mixIDs;

}
