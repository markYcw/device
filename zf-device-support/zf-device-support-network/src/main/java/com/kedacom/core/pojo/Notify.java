package com.kedacom.core.pojo;

/**
 * @author van.shu
 * @create 2021/5/13 20:26
 */
public interface Notify {

    /**
     * 流水号
     * @return
     */
    Integer acquireSsno();

    /**
     * 命令值，可以理解为通知类型
     * @return
     */
    String acquireCommand();

}
