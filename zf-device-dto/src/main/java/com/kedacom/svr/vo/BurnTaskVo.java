package com.kedacom.svr.vo;

import com.kedacom.svr.pojo.BurnTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/14 14:49
 * @description
 */
@Data
public class BurnTaskVo {

    @ApiModelProperty("刻录任务列表")
    private List<BurnTask> burnTaskList;

}
