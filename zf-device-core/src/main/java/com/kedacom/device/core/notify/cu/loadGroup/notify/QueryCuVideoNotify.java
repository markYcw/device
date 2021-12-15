package com.kedacom.device.core.notify.cu.loadGroup.notify;

import com.kedacom.device.core.notify.cu.loadGroup.pojo.QueryVideoInsideNotify;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/12/2 19:32
 * @description 查询录像通知
 */
@ToString(callSuper = true)
@Data
public class QueryCuVideoNotify extends CuNotify {

    @ApiModelProperty("录像通知内容")
    private QueryVideoInsideNotify content;

}
