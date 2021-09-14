package com.kedacom.svr.dto;

import com.kedacom.svr.pojo.RemotePoint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/10 14:33
 * @description 启用/停止远程点
 */
@Data
public class RemotePointDto extends SvrRequestDto{

    @ApiModelProperty("0：启动远程点 1：停止远程点")
    private Integer type;

    @ApiModelProperty("远程点列表")
    private List<RemotePoint> remotePointList;

}
