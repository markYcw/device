package com.kedacom.core.proxy;

import com.kedacom.core.NIOConnector;
import com.kedacom.core.pojo.ClientInfo;
import com.kedacom.core.pojo.Request;
import com.kedacom.core.pojo.Response;
import com.kedacom.core.spring.NotifyContext;
import com.kedacom.exception.KMException;
import com.kedacom.exception.KMTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author van.shu
 * @date 2021/5/12 7:59
 */
@Slf4j
public class NetworkProxy implements InvocationHandler, FactoryBean<Object>, ApplicationContextAware {

    private NIOConnector connector;

    private Class<?> type;

    private ClientInfo clientInfo;

    private ApplicationContext context;

    public NetworkProxy() {

    }

    public NetworkProxy(NIOConnector connector, Class<?> type,ClientInfo clientInfo) {

        this.connector = connector;

        this.type = type;

        this.clientInfo = clientInfo;

    }


    /**
     * 创建并获取代理对象
     *
     * @param <T>
     * @return
     */
    public  <T> T newInstance() {
        ClassLoader classLoader = type.getClassLoader();
        //Class[] classes = {type};
        T proxy = (T) Proxy.newProxyInstance(classLoader, new Class<?>[]{type}, this);
        return proxy;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler =
                        args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if ("hashCode".equals(method.getName())) {
            return hashCode();
        } else if ("toString".equals(method.getName())) {
            return toString();
        }

        log.info("我来代理发请求啦，参数是 args :{}", args);

        Map<Method, ClientInfo.MethodInfo> methodInfos = clientInfo.getMethodInfos();

        if (CollectionUtils.isEmpty(methodInfos)) {
            throw new IllegalArgumentException("proxy client method info is null");
        }



        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof Request) {
                    ClientInfo.MethodInfo methodInfo = methodInfos.get(method);
                    Class<?> returnType = methodInfo.getReturnType();
                    Integer ssno = ((Request) arg).acquireSsno();
                    CompletableFuture<Response> future = connector.sendRequest((Request) arg,returnType);
                    try {
                        return future.get(clientInfo.getTimeout(), TimeUnit.MILLISECONDS);
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("execute request error , request ssno [{}] ,e", ssno, e);
                        throw new KMException("execute request error", e);
                    } catch (TimeoutException e) {
                        log.error("execute request timeout ! request ssno is [{}] wait time is [{}]  ms ,and caused by : ", clientInfo.getTimeout(), ssno, e);
                        throw new KMTimeoutException("execute request timeout ! request ssno is "+ssno+" and wait time is " + clientInfo.getTimeout() + " ms");
                    }
                }

            }
        }
        return null;
    }

    @Override
    public Object getObject() throws Exception {
        NotifyContext notifyContext = connector.getNotifyContext();

        if (notifyContext.getApplicationContext() == null) {
            notifyContext.setApplicationContext(context);
        }

        Object proxy = newInstance();
        log.info("proxy = {}", proxy);
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
