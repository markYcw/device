package com.kedacom.mt;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangxy
 * @describe
 * @date 2021/12/2
 */
@Data
public class StartP2P implements Serializable {

    /**
     * 码率 : 512/768/1024/4096
     */
    private Integer rate;

    /**
     * 终端
     */
    private RemoteMt remoteMt;

}
