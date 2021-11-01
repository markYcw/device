package com.kedacom.cu.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取域链表响应体
 * @author ycw
 * @version v1.0
 * @date 2021/9/7 10:18
 * @description
 */
@Data
public class Domains implements Serializable {

    @ApiModelProperty("平台域id")
    private Integer domainId;

    @ApiModelProperty("域名称")
    private String name;

    @ApiModelProperty("父域id")
    private String parentId;

}
