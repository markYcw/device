package com.kedacom.device.core.exception;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.kedacom.power.model.KmResultCodeEnum;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName IChecker
 * @Description 校验基础类
 * @Author zlf
 * @Date 2021/6/1 15:49
 */
public interface IChecker {

    /**
     * @Description 创建异常
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    PowerServiceException newException(KmResultCodeEnum codeEnum);

    /**
     * @Description 参数不为空，为空的话抛出异常
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    default void notNull(Object obj, KmResultCodeEnum codeEnum) {
        if ((obj instanceof String && StrUtil.isEmpty(obj.toString()))
                || (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj))
                || (obj instanceof Map && MapUtil.isEmpty((Map) obj))
                || (obj == null)) {
            throw newException(codeEnum);
        }
    }

    /**
     * @Description 参数为空，不为空的话抛出异常
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    default void isNull(Object obj, KmResultCodeEnum codeEnum) {
        if ((obj instanceof String && StrUtil.isNotBlank(obj.toString()))
                || (obj instanceof Collection && CollectionUtils.isNotEmpty((Collection) obj))
                || (obj instanceof Map && MapUtil.isNotEmpty((Map) obj))
                || (obj != null)) {
            throw newException(codeEnum);
        }
    }

    /**
     * @Description 断言条件表达式是否成立，不成立则抛出异常
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    default void isTrue(Boolean condition, KmResultCodeEnum codeEnum) {
        if (!condition) {
            throw newException(codeEnum);
        }
    }

}
