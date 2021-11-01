package com.kedacom.cu.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 获取平台时间响应体
 * @author ycw
 * @version v1.0
 * @date 2021/11/1 13:53
 * @description
 */
@Data
public class TimeVo {

    @ApiModelProperty("时间")
    private String time;

}
