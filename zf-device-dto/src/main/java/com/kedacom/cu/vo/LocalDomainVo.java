package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 6.3获取平台域信息响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:18
 * @description
 */
@Data
public class LocalDomainVo implements Serializable {

    @ApiModelProperty("平台域id")
    private String domainId;

}
