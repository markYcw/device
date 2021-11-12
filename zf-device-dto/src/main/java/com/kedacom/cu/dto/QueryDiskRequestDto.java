package com.kedacom.cu.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
@ApiModel(description = "查询磁阵（磁盘）信息请求参数类")
public class QueryDiskRequestDto extends CuRequestDto implements Serializable {
}
