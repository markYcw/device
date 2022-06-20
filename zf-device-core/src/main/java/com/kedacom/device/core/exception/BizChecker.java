package com.kedacom.device.core.exception;


import com.kedacom.power.model.KmResultCodeEnum;

/**
 * @ClassName ParamChecker
 * @Description 校验断言器
 * @Author zlf
 * @Date 2021/6/1 15:56
 */
public interface BizChecker extends IChecker {

    @Override
    default PowerServiceException newException(KmResultCodeEnum codeEnum) {
        throw new PowerServiceException(codeEnum);
    }
}
