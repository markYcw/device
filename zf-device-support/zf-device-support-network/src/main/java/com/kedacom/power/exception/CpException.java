package com.kedacom.power.exception;

/**
 * @author hxj
 * @date 2022/5/7 14:16:01
 */
public class CpException extends RuntimeException {
    /**
     * 异常原因
     * @param reason
     */
    public CpException(String reason) {
        super(reason);
    }
}
