package com.kedacom.vs.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 13:41
 */
@Data
public class RecInfoVo {


    @ApiModelProperty("录像名称")
    private String name;

    @ApiModelProperty("rtsp播放url")
    private String rtspUrl;

    @ApiModelProperty("http播放url")
    private String httpUrl;

    @ApiModelProperty("开始时间")
    private String createTime;

    @ApiModelProperty("录像持续时间(单位:秒)")
    private Integer duration;

}
