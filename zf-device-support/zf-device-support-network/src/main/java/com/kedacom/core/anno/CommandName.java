package com.kedacom.core.anno;

import java.lang.annotation.*;

/**
 * 用于标注方法对应的命令值
 * 不加，默认为方法名小写
 *
 * @author van.shu
 * @date 2021/5/12 7:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandName {

    String name() default "";

}
