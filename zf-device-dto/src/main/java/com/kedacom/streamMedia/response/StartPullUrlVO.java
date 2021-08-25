package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 14:58
 */
@Data
@ApiModel(description =  "开始拉取实时网络媒体流出参")
public class StartPullUrlVO implements Serializable {

    private Integer sessionID;

}
