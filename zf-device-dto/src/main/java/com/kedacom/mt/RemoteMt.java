package com.kedacom.mt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class RemoteMt implements Serializable {

    private String ip;

    /**
     * 0:ip,1:e164,2:h323id,3:dialnum,4:sipaddr
     */
    private Integer type;

    /**
     * 终端别名
     */
    private String alias;

}
