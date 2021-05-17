package com.kedacom.core.anno;

import java.lang.annotation.*;

/**
 * 修饰通知类型
 * @author van.shu
 * @create 2021/5/17 9:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KmNotify {

    //定义通知的具体类型
    String name() default "";
}
