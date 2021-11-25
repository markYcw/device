package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/12
 */
@Data
@ApiModel(description = "关闭平台录像请求参数类")
public class PlatRecStopVo extends RecBaseVo implements Serializable {
}
