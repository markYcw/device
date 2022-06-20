package com.kedacom.newMedia.resopnse;

import com.kedacom.newMedia.pojo.BurnInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/4/11 15:37
 * @description 设备信息响应体
 */
@Data
public class GetBurnStateResponse extends NewMediaResponse {

    @ApiModelProperty(value = "刻录信息")
    private BurnInfo burnInfo;

}
