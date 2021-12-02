package com.kedacom.mt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class SetDumbOrMuteVo implements Serializable {

    /**
     * 0:非静音/非哑音 1:静音/哑音
     */
    private Integer state;

}
