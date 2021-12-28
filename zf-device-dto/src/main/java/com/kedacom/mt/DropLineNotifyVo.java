package com.kedacom.mt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/3
 */
@Data
public class DropLineNotifyVo extends MtNotifyVo implements Serializable {

    private ContentVo content;

}
