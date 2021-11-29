package com.kedacom.deviceListener.notify.cu;

import com.kedacom.deviceListener.notify.DeviceNotifyRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/29 11:21
 * @description
 */
@Data
@ApiModel(description = "监控平台设备录像状态变更通知")
public class CuChnStatusDTO extends DeviceNotifyRequestDTO {

    @ApiModelProperty("设备puId")
    private String puId;

    @ApiModelProperty("视频源信息")
    private List<SrcChsVo> srcChsVos;

}
