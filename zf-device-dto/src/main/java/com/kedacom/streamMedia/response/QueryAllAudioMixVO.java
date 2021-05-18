package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hxj
 * @Date: 2021/5/18 15:27
 */
@Data
public class QueryAllAudioMixVO implements Serializable {

    @ApiModelProperty("画面合成ID集合")
    private List<String> mixIDs;

}