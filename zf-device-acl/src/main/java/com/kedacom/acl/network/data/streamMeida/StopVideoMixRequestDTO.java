package com.kedacom.acl.network.data.streamMeida;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/4/30 14:27
 */
@Data
@ApiModel(description = "停止画面合成业务交互参数")
public class StopVideoMixRequestDTO implements Serializable {

    @ApiModelProperty("使用32位UUID（无横线）")
    private String GroupID;

    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

}
