package com.kedacom.aiBox.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/19
 */
@Data
public class AiBoxContrastDto implements Serializable {

    @ApiModelProperty("ID")
    private String ID;

    @ApiModelProperty("姓名")
    private String Name;

    @ApiModelProperty("相似度")
    private Integer Similarity;

}
