package com.kedacom.acl.network.anno;

import java.lang.annotation.*;

/**
 * 标注在方法参数上
 * name可以指定序列化的key名称
 * 默认为方法形参名称
 * @author van.shu
 * @create 2021/5/12 10:02
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String name() default "";
}
