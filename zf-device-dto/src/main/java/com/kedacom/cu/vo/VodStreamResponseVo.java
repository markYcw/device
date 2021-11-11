package com.kedacom.cu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxy
 * @describe
 * @date 2021/11/11
 */
@Data
@ApiModel(description = "发布点播响应参数类")
public class VodStreamResponseVo implements Serializable {

    private String error;

    private List<VodStreamDetailResponseVo> urls;

}
