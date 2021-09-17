package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 13:51
 * @description dvd状态
 */
@Data
public class DvdContent {

    @ApiModelProperty("刻录任务ID")
    private List<DvdInfo> dvdList;

}
