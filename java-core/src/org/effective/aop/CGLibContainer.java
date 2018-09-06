package org.effective.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ClassUtils.getMethod;


public class CGLibContainer {

    static Map<Class<?>, Map<InterceptPoint, List<Method>>> interceptMethodsMap
            = new HashMap<>();

    static Class<?>[] aspects = new Class<?>[]{ExceptionAspect.class, ServiceLogAspect.class};

    static {
        try {
            init();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws NoSuchMethodException {
        for (Class<?> cls : aspects) {
            Aspect aspect = cls.getAnnotation(Aspect.class);
            if (aspect != null) {

                Method before = null;
                try {
                    before = getMethod(cls, "before",
                            new Class<?>[]{Object.class, Method.class, Object[].class});
                } catch (Exception e){
                }

                Method after = null;
                try {
                    after = getMethod(cls, "after",
                            new Class<?>[]{Object.class, Method.class, Object[].class, Object.class});
                } catch (Exception e) {
                }

                Method exception = null;
                try {
                    getMethod(cls, "exception",
                            new Class<?>[]{Object.class, Method.class, Object[].class, Throwable.class});
                } catch (Exception e){
                }

                Class<?>[] interceptedAttr = aspect.value();

                for (Class<?> intercepted : interceptedAttr) {
                    addInterceptMethod(intercepted, InterceptPoint.BEFORE, before);
                    addInterceptMethod(intercepted, InterceptPoint.AFTER, after);
                    addInterceptMethod(intercepted, InterceptPoint.EXCEPTION, exception);
                }
            }
        }
    }

    private static void addInterceptMethod(Class<?> cls, InterceptPoint point,
                                           Method method) {
        if (method == null) {
            return;
        }

        Map<InterceptPoint, List<Method>> map = interceptMethodsMap.get(cls);
        if (map == null) {
            map = new HashMap<>();
            interceptMethodsMap.put(cls, map);
        }

        List<Method> methods = map.get(point);
        if (methods == null) {
            methods = new ArrayList<>();
            map.put(point, methods);
        }

        methods.add(method);
    }

    public static <T> T createInstance(Class<T> cls) throws IllegalAccessException, InstantiationException {
        Map<InterceptPoint, List<Method>> map = interceptMethodsMap.get(cls);
        if (map == null) {
            return (T) cls.newInstance();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new AspectInterceptor());
        return (T) enhancer.create();
    }

    public static <T> T getInstance(Class<T> cls) {
        try {
            T obj = createInstance(cls);
            Field[] fields = cls.getDeclaredFields();

            for (Field f : fields) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }

                Class<?> fieldCls = f.getType();
                f.set(obj, createInstance(fieldCls));
            }

            return obj;
        } catch (Exception e) {
           throw new RuntimeException();
        }
    }


    public static class AspectInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {
            List<Method> beforeMethods = getInterceptMethods(o.getClass()
                            .getSuperclass(),
                    InterceptPoint.BEFORE);
            for (Method m : beforeMethods) {
                m.invoke(null, new Object[]{o, method, args});
            }

            try {
                Object result = proxy.invokeSuper(o, args);
                List<Method> afterMethods = getInterceptMethods(o.getClass()
                                .getSuperclass(),
                        InterceptPoint.AFTER);
                for (Method m : afterMethods) {
                    m.invoke(null, new Object[]{o, method, args, result});
                }
                return result;
            } catch (Throwable t) {
                List<Method> exceptionMethods = getInterceptMethods(o.getClass()
                                .getSuperclass()
                        , InterceptPoint.EXCEPTION);

                for (Method m : exceptionMethods) {
                    m.invoke(null, new Object[]{o, method, args, t});
                }

                throw t;
            }
        }
    }

    static private List<Method> getInterceptMethods(Class<?> cls, InterceptPoint point) {
        Map<InterceptPoint, List<Method>> map = interceptMethodsMap.get(cls);
        if (map == null) {
            return Collections.emptyList();
        }

        List<Method> methods = map.get(point);
        if (methods == null) {
            return Collections.emptyList();
        }

        return methods;
    }
}
