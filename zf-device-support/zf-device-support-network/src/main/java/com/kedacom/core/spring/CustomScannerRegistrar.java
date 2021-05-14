package com.kedacom.core.spring;

import com.kedacom.core.NIOConnector;
import com.kedacom.core.anno.EnableKmProxy;
import com.kedacom.core.anno.KmProxy;
import com.kedacom.core.config.NetworkConfig;
import com.kedacom.core.pojo.ClientInfo;
import com.kedacom.core.proxy.NetworkProxy;
import com.kedacom.exception.KMException;
import com.kedacom.exception.KMProxyException;
import com.kedacom.network.niohdl.core.IoContext;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar{

    private NetworkConfig networkConfig;

    private NIOConnector connector;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {


        Map<String, Object> attrs = annotationMetadata
                .getAnnotationAttributes(EnableKmProxy.class.getName(), true);

        if (attrs != null && attrs.containsKey("ipAddr")) {
            initIoContext();
            initNetworkConfig((String) attrs.get("ipAddr"));
        }

        Set<String> basePackages = getBasePackages(annotationMetadata);

        for (String basePackage : basePackages) {

            Reflections reflections = new Reflections(basePackage);

            //获取带有@KmProxy注解的类
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(KmProxy.class);

            for (Class<?> clazz : typesAnnotatedWith) {

                handlerProxyClient(clazz,registry);

            }

        }


    }

    private void initIoContext() {
        IoContext.initIoSelector();
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

    private void handlerProxyClient(Class<?> clazz, BeanDefinitionRegistry registry) {

        //这个类必须是接口并且被@KmProxy标注
        checkProxyClient(clazz);

        //解析这个类并组装信息
        ClientInfo clientInfo = resolverClient(clazz);

        //注册代理类
        registerProxyClient(registry,clientInfo);


    }

    private void registerProxyClient(BeanDefinitionRegistry registry, ClientInfo clientInfo) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(NetworkProxy.class);

        initConnectorIfNot();

        builder.addConstructorArgValue(connector);

        builder.addConstructorArgValue(clientInfo.getClazz());

        String realBeanName = clientInfo.getClientBeanName() + "." + NetworkProxy.class.getSimpleName();

        clientInfo.setClientBeanName(realBeanName);

        registry.registerBeanDefinition(realBeanName, builder.getBeanDefinition());

    }

    private void initConnectorIfNot() {
        if (connector == null) {
            try {
                connector = NIOConnector.startWith(networkConfig.getServerIp(), networkConfig.getServerPort());
            } catch (IOException e) {
                log.error("init socketChannel failed ,", e);
                throw new KMException("init socketChannel failed");
            }

        }
    }

    private void initNetworkConfig(String serverIpAddr) {

        if (StringUtils.isEmpty(serverIpAddr)) {

            networkConfig = new NetworkConfig();

            return;
            //throw new IllegalArgumentException("ipAddr is empty , cannot init proxy bean");
        }

        String[] split = serverIpAddr.split(":");

        if (split.length != 2) {
            throw new IllegalArgumentException("illegal ipAddr : " + serverIpAddr + ", cannot init proxy bean");
        }

        networkConfig = new NetworkConfig();

        networkConfig.setServerIp(split[0]);

        networkConfig.setServerPort(Integer.parseInt(split[1]));


    }


    /**
     * 解析操作
     * @param clazz class对象
     */
    private ClientInfo resolverClient(Class<?> clazz) {

        //唯一标识
        String key = clazz.getSimpleName();

        String attributeNameValue = clazz.getAnnotation(KmProxy.class).name();

        if (!StringUtils.isEmpty(attributeNameValue)) {
            key = attributeNameValue;
        }

        Set<ClientInfo.MethodInfo> methodInfos = new HashSet<>();

        //获取接口上的方法
        Method[] methods = clazz.getMethods();

        //这里需要说明一下：这些代理类的参数和返回值都必须是Request和Response类型的，不然会导致请求失败

        ClientInfo.MethodInfo methodInfo;
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            Parameter[] parameters = method.getParameters();
            methodInfo = ClientInfo.MethodInfo.builder()
                    .method(method)
                    .parameters(parameters)
                    .returnType(returnType)
                    .build();
            methodInfos.add(methodInfo);
        }

        return ClientInfo.builder()
                .clazz(clazz)
                .clientBeanName(key)
                .methodInfos(methodInfos)
                .build();

    }


    private void checkProxyClient(Class<?> clazz) {

        if (!(clazz.isInterface() && clazz.isAnnotationPresent(KmProxy.class))) {
            log.error("@KmProxy can only be specified on an interface");
            throw new KMProxyException("@KmProxy can only be specified on an interface");
        }
    }

}
