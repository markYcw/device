package com.kedacom.core.anno;

import com.kedacom.core.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 用于开启K-M代理功能
 *
 * @author van.shu
 * @date 2021/5/12 7:13
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistrar.class)
@Documented
public @interface EnableKmProxy {

    /**
     * 包扫描路径
     * @return 路径数组
     */
    String[] basePackages() default {};

    /**
     * 绑定地址 eg: 127.0.0.1:8080
     * @return 绑定地址
     */
    String ipAddr() default "";

}
