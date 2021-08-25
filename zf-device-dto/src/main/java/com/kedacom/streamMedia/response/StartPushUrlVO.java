package com.kedacom.streamMedia.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hxj
 * @Date: 2021/7/19 13:58
 */
@Data
@ApiModel(description =  "开始推送媒体流出参")
public class StartPushUrlVO implements Serializable {

    private Integer sessionID;

}
