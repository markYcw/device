package com.kedacom.vs.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @create 2021/06/23 13:41
 */
@Data
public class LiveInfoVo {


    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("hls的播放url")
    private String rtspUrl;

}
