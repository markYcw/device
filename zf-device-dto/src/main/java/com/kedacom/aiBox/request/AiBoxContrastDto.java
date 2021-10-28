package com.kedacom.aiBox.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/28
 */
@Data
public class AiBoxContrastDto implements Serializable {

    private String Name;

    @JsonProperty("ID num")
    private String ID;

    private String FeatureLibImg;

}
