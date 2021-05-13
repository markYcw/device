package com.kedacom.core.spring;

import com.kedacom.core.anno.EnableKmProxy;
import com.kedacom.core.anno.KmProxy;
import com.kedacom.core.proxy.NetworkProxy;
import com.kedacom.exception.KMProxyException;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 注册包扫描器
 *
 * @author van.shu
 * @date 2021/5/12 7:17
 */
@Slf4j
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, ApplicationContextAware {


    private ResourceLoader resourceLoader;

    private ApplicationContext applicationContext;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {


        Set<String> basePackages = getBasePackages(annotationMetadata);

        for (String basePackage : basePackages) {

            Reflections reflections = new Reflections(basePackage);

            //获取带有@KmProxy注解的类
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(KmProxy.class);

            for (Class<?> clazz : typesAnnotatedWith) {

                handlerProxyClient(clazz);

            }

            //扫描包下面的类是否含有@KmProxy注解的接口

            //解析每个方法以及每个方法上的参数和注解，保存相关信息用来映射和解析响应

            //将含有@KmProxy注解的接口生成动态代理对象

            //注入到spring ioc 容器中


        }


    }

    private Set<String> getBasePackages(AnnotationMetadata annotationMetadata) {

        Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(EnableKmProxy.class.getCanonicalName());

        Set<String> basePackages = new HashSet<>();

        for (String pkg : (String[]) attributes.get("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        if (basePackages.isEmpty()) {
            basePackages.add(
                    ClassUtils.getPackageName(annotationMetadata.getClassName()));
        }

        return basePackages;
    }

    private void handlerProxyClient(Class<?> clazz) {

        //这个类必须是接口并且被@KmProxy标注
        checkProxyClient(clazz);

        //解析这个类并组装信息
        resolverClient(clazz);

        getTarget(clazz);


    }

    private Object getTarget(Class<?> clazz) {

        //NetworkProxy proxy = new NetworkProxy();
        return null;

    }

    /**
     * 解析操作
     * @param clazz class对象
     */
    private void resolverClient(Class<?> clazz) {

        //唯一标识
        String key = clazz.getSimpleName();

        String attributeNameValue = clazz.getAnnotation(KmProxy.class).name();

        if (!StringUtils.isEmpty(attributeNameValue)) {
            key = attributeNameValue;
        }

        //获取接口上的方法
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();

        }



    }


    private void registerClients() {

    }



    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    private void checkProxyClient(Class<?> clazz) {

        if (!(clazz.isInterface() && clazz.isAnnotationPresent(KmProxy.class))) {
            log.error("@KmProxy can only be specified on an interface");
            throw new KMProxyException("@KmProxy can only be specified on an interface");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
