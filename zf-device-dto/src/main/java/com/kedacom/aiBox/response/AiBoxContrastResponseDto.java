package com.kedacom.aiBox.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/10/28
 */
@Data
public class AiBoxContrastResponseDto implements Serializable {

    private Boolean flag = false;

    private String data;

    private String errorMessage;

}
