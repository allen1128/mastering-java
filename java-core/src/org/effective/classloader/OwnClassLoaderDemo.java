package org.effective.classloader;

public class OwnClassLoaderDemo {
    public static void main(String [] args) throws ClassNotFoundException {
        String className = "org.effective.classloader.ServiceApiImpl";

        ClassLoader classLoader = new OwnClassLoader();
        Class<?> klass1 = classLoader.loadClass(className);

        classLoader = new OwnClassLoader();
        Class<?> klass2 = classLoader.loadClass(className);

        if (klass1 != klass2) {
            System.out.println("classes are diff");
        }
    }
}
