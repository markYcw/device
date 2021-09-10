package com.kedacom.svr.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取SVR时间响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:55
 * @description
 */
@Data
public class SvrTimeVo implements Serializable {

    @ApiModelProperty("时间")
    private String time;
}
