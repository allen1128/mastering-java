package com.xl.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainTest {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        SingletonClass sc = SingletonClass.getInstance();

        Class<?> clazz = SingletonClass.class;
        try {
            Constructor c = clazz.getDeclaredConstructor(null);
            c.setAccessible(true);

            Object o1 = c.newInstance();
            Object o2 = c.newInstance();

            System.out.println(o1 == o2);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
