package com.kedacom.acl.network.anno;

import java.lang.annotation.*;

/**
 * 作用在方法参数上
 * 用来标注该对象是包装的请求体
 * tips:包装的请求体里面的属性必须和接口文档中定义的名称一致
 * @author van.shu
 * @create 2021/5/12 10:09
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {

}
