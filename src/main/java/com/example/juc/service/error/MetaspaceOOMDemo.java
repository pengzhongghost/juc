package com.example.juc.service.error;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * java.lang.OutOfMemoryError: Metaspace
 * -XX:MaxMetaspaceSize=9m -XX:MetaspaceSize=9m
 */
public class MetaspaceOOMDemo {

    static class OOMTest {

    }

    public static void main(String[] args) {
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invoke(o, objects);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println(i);
            e.printStackTrace();
        }
    }
}
