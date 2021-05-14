package com.kedacom.core.pojo;

import lombok.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

/**
 * @author van.shu
 * @create 2021/5/12 13:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ClientInfo {

    /**
     * 对应接口的name字段的命令值
     */
    private String command;

    /**
     * BeanName
     */
    private String clientBeanName;

    /**
     * 类型
     */
    private Class<?> clazz;

    private Set<MethodInfo> methodInfos;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
   public static class MethodInfo {

        private Method method;

        private Class<?> returnType;

        private Parameter[] parameters;

    }

}