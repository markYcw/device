package com.kedacom.vs.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 13:41
 */
@Data
public class RecInfoHttpVo {


    @ApiModelProperty("录像名称")
    private String name;

    @ApiModelProperty("http的播放链接")
    @JSONField(name = "httpUrl")
    private String url;

    @ApiModelProperty("开始时间")
    @JSONField(name = "createTime")
    private String starttime;

    @ApiModelProperty("录像持续时间(单位:秒)")
    private Integer duration;

}
