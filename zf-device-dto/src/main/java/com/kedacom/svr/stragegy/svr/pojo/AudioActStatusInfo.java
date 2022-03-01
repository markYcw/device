package com.kedacom.svr.stragegy.svr.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/17 14:10
 * @description 通知内容
 */
@Data
public class AudioActStatusInfo {

    @ApiModelProperty("状态 0:关闭 1:开启")
    private Integer state;

}
