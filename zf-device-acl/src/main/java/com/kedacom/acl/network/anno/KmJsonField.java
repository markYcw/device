package com.kedacom.acl.network.anno;

import java.lang.annotation.*;

/**
 * @author van.shu
 * @create 2021/5/12 14:50
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KmJsonField {

    String name() default "";

    boolean ignore() default false;

}
