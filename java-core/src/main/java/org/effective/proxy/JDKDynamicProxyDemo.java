package org.effective.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxyDemo {

    public static interface IService {
        public void sayHello();
    }

    public static class RealObject implements IService {
        @Override
        public void sayHello() {
            System.out.println("Hello world!");
        }
    }

    public static class SimpleInvocationHandler implements InvocationHandler {
        private IService realObject;

        public SimpleInvocationHandler(IService object) {
            realObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before invoking");
            Object result = method.invoke(realObject, args);
            System.out.println("after invoking");
            return result;
        }
    }

    public static void main(String[] args) {
        IService realObject = new RealObject();
        IService proxyObject = (IService) Proxy.newProxyInstance(
                IService.class.getClassLoader(),
                new Class<?>[]{IService.class},
                new SimpleInvocationHandler(realObject));
        proxyObject.sayHello();
    }


}
