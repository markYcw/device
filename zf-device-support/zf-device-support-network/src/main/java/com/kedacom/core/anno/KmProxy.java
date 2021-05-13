package com.kedacom.core.anno;

import java.lang.annotation.*;

/**
 * @author van.shu
 * @date 2021/5/12 7:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KmProxy {


    String name() default "";

}
