package com.kedacom.svr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.svr.pojo.Case;
import com.kedacom.svr.pojo.Hum;
import com.kedacom.svr.pojo.Subtitle;
import com.kedacom.svr.pojo.Time;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 19:51
 * @description 设置画面叠加
 */
@Data
public class OsdSetVo extends SvrRequestDto{

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
