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

    /**
     * commandName策略，默认方法名小写
     *
     * @return 策略
     */
    CommandNameStrategy COMMAND_NAME_STRATEGY() default CommandNameStrategy.LOWERCASE;


    enum CommandNameStrategy {
        LOWERCASE;
    }

}
