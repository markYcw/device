package com.kedacom.device.core.basicParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/15 9:21
 * @description 重连监控平台事迹发布类
 */
@Data
public class CuReLoginParam {

    @ApiModelProperty("数据库ID")
    private Integer dbId;

}
