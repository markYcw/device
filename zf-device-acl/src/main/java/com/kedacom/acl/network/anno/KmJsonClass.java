package com.kedacom.acl.network.anno;

import java.lang.annotation.*;

/**
 * @author van.shu
 * @create 2021/5/12 14:53
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KmJsonClass {

    String name() default "";

}
