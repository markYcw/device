package com.kedacom.device.core.event;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxj
 * @date: 2021/8/17 10:01
 * @description 设备与分组状态变更kafka通知类
 */
@Data
@ApiModel(description =  "设备与分组状态变更kafka通知类")
public class DeviceAndGroupModel implements Serializable {

    private List<DevAndGroup> devandgroup;

}
