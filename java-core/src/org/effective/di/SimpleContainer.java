package org.effective.di;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class SimpleContainer {
    private static Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public static <T> T getInstance(Class<T> cls) {
        try {
            if (!cls.isAnnotationPresent(SimpleSingleton.class)) {
                return createInstance(cls);
            }

            Object o = instances.get(cls);
            if (o == null) {
                synchronized (cls) {
                    o = instances.get(cls);
                    if (cls == null) {
                        o = createInstance(cls);
                        instances.put(cls, o);
                    }
                }
            }
            return (T) o;
        } catch (Exception ex) {
            throw new RuntimeException(ex.toString());
        }
    }

    public static <T> T createInstance(Class<T> cls) throws IllegalAccessException, InstantiationException {
        T obj = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(SimpleInject.class)) {
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }

                Object toBeInjected = getInstance(f.getType());
                f.set(obj, toBeInjected);
            }
        }

        return obj;
    }
}
