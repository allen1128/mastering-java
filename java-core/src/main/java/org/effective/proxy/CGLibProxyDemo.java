package org.effective.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLibProxyDemo {

    public static class RealService {
        public void sayHello() {
            System.out.println("Hello world!");
        }
    }

    private static class SimpleInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("Entering method");
            Object object = proxy.invokeSuper(o, args);
            System.out.println("Exiting method");
            return object;
        }
    }

    public static <T> T getProxy(Class<T> klass){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(klass);
        enhancer.setCallback(new SimpleInterceptor());
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        RealService proxyService = getProxy(RealService.class);
        proxyService.sayHello();
    }
}
