package com.kedacom.core.pojo;

import com.kedacom.core.anno.CommandName;
import com.kedacom.core.anno.KmProxy;
import com.kedacom.core.anno.KmRequestBody;
import com.kedacom.core.client.TestClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author van.shu
 * @date 2021/5/12 7:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher implements Serializable {

    private String name;

    private int age;

    private List<User> userList;

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        Class<TestClient> clazz = TestClient.class;

        String simpleName = clazz.getSimpleName();
        System.out.println("simpleName = " + simpleName);
        String name = clazz.getName();
        System.out.println("name = " + name);
        String typeName = clazz.getTypeName();
        System.out.println("typeName = " + typeName);
        System.out.println("-----------------------");
        KmProxy kmProxy = clazz.getAnnotation(KmProxy.class);

        String proxyName = kmProxy.name();

        System.out.println("proxyName = " + proxyName);

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
           // System.out.println("method = " + method);
            String methodName = method.getName();
            String command = methodName.toLowerCase();
            System.out.println("methodName = " + methodName);
            if (method.isAnnotationPresent(CommandName.class)) {
                CommandName commandName = method.getAnnotation(CommandName.class);
                String attributeValue = commandName.name();
                if (!StringUtils.isEmpty(attributeValue)) {
                    command = attributeValue;
                }
            }
            System.out.println("command = " + command);

            Parameter[] parameters = method.getParameters();

            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(KmRequestBody.class)) {
                    KmRequestBody kmRequestBody = parameter.getAnnotation(KmRequestBody.class);
                    Class<?> paramClazz = parameter.getType();
                    Field[] declaredFields = paramClazz.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        String declaredFieldName = declaredField.getName();
                        Class<?> declaredFieldType = declaredField.getType();
                        System.out.println("declaredFieldName = " + declaredFieldName + "--declaredFieldType = " + declaredFieldType);
                    }

                }
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            Class<?> returnType = method.getReturnType();


            String returnTypeName = returnType.getName();

            System.out.println("returnTypeName = " + returnTypeName);

            Field[] returnTypeDeclaredFields = returnType.getDeclaredFields();

            int length = returnTypeDeclaredFields.length;

            System.out.println("length = " + length);

            if (ReflectUtil.isPrimitive(returnType)) {
                returnType = ReflectUtil.getPrimitiveWrapper(returnType);
                returnTypeDeclaredFields = returnType.getDeclaredFields();
            }


            for (Field declaredField : returnTypeDeclaredFields) {
                String declaredFieldName = declaredField.getName();
                Class<?> declaredFieldType = declaredField.getType();
                System.out.println("RETURN======declaredFieldName = " + declaredFieldName + "--declaredFieldType = " + declaredFieldType);
            }

            System.out.println("---------------------");

        }



    }
}
