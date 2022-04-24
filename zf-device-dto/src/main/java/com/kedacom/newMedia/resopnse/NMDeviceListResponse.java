package com.kedacom.newMedia.resopnse;

import com.kedacom.newMedia.pojo.NMDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2022/4/11 15:37
 * @description 设备信息响应体
 */
@Data
public class NMDeviceListResponse extends NewMediaResponse {

    @ApiModelProperty(value = "设备列表")
    private List<NMDevice> devList;

    @ApiModelProperty(value = "总数")
    private Integer total;


}
