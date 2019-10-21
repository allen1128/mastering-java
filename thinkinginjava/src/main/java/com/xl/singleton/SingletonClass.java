package com.xl.singleton;

public class SingletonClass {

    private static SingletonClass instance = new SingletonClass();

    private SingletonClass() {
        if (instance != null) {
            throw new RuntimeException("double initialzition");
        }
    }

    public static SingletonClass getInstance() {
        return instance;
    }

}
