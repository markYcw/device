package com.kedacom.mt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class PtzCtrlVo implements Serializable {

    private Integer type;

    private Integer ptzCmd;

}
