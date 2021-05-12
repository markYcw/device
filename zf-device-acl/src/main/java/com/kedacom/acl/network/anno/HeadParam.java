package com.kedacom.acl.network.anno;

import java.lang.annotation.*;

/**
 * 作用在方法参数上
 * 标注了这个注解的方法参数，将会被解析到head中
 * key默认为参数名称，value为实参
 *
 * @author van.shu
 * @date 2021/5/12 7:15
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeadParam {

    /**
     * 指定key的名称
     * 可以不填，默认为参数名称
     *
     * @return
     */
    String name() default "";

}
