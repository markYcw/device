package com.kedacom.core.anno;

import com.kedacom.core.spring.ClientProxyRegister;
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
@Import(ClientProxyRegister.class)
@Documented
public @interface EnableKmProxy {

    /**
     * 代理接口扫描路径
     * @see KmProxy
     * @return 路径数组
     */
    String[] proxyPackages() default {};


    /**
     * 通知类扫描路径
     * @return 路径数组
     * @see KmNotify
     */
    String[] notifyPackages() default {};


    /**
     * 绑定地址 eg: 127.0.0.1:8080
     * @return 绑定地址
     */
    String ipAddr() default "";

}
