package org.effective.aop;

import org.effective.aop.ServiceA;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect({ServiceB.class, ServiceA.class})
public class ServiceLogAspect {
    public static void before(Object object, Method method, Object[] args) {
        System.out.println("entering " +
                method.getDeclaringClass().getSimpleName() + " with args="
                + Arrays.toString(args));
    }

    public static void after(Object obect, Method method, Object[] args, Object result) {
        System.out.println("leaving " +
                method.getDeclaringClass().getSimpleName() + " with result="
                + result);
    }
}
