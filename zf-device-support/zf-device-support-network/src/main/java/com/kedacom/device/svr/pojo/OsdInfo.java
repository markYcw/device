package com.kedacom.device.svr.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 18:53
 * @description 画面叠加参数
 */
@Data
public class OsdInfo {

    @ApiModelProperty("温湿度叠加")
    private Hum hum;

    @ApiModelProperty("案件叠加")
    @JSONField(name = "case")
    private Case caseVo;

    @ApiModelProperty("时间叠加")
    private Time time;

    @ApiModelProperty("时间叠加")
    private Subtitle subtitle;



}
