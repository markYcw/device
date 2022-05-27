package com.kedacom.streamMedia.response;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/8 11:25
 * @description
 */
@ApiModel(description = "获取当前语音激励状态")
@Data
public class GetSvrAudioActStateVo implements Serializable {

    @ApiModelProperty("激励开关，0-关，1-开")
    @JSONField(name = "actCfg")
    private Integer ActCfg;


}
