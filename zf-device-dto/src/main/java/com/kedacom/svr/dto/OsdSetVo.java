package com.kedacom.svr.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.kedacom.svr.pojo.Case;
import com.kedacom.svr.pojo.Hum;
import com.kedacom.svr.pojo.Subtitle;
import com.kedacom.svr.pojo.Time;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 19:51
 * @description 设置画面叠加
 */
@Data
public class OsdSetVo extends SvrRequestDto{

    @Valid
    @ApiModelProperty(value = "温湿度叠加",required = true)
    private Hum hum;

    @Valid
    @ApiModelProperty(value = "案件叠加",required = true)
    @JSONField(name = "case")
    private Case caseVo;

    @Valid
    @ApiModelProperty(value = "时间叠加",required = true)
    private Time time;

    @Valid
    @ApiModelProperty(value = "时间叠加",required = true)
    private Subtitle subtitle;

}
