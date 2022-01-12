package com.kedacom.vs.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @create 2021/06/23 13:41
 */
@Data
public class LiveInfoVo {


    @ApiModelProperty("总数")
    private Integer total;

    @ApiModelProperty("直播列表")
    private List<LiveInfo> lives;

}
