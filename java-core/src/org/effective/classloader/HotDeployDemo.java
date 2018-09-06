package org.effective.classloader;

import java.io.File;

public class HotDeployDemo {
    private static final String CLASS_NAME = "org.effective.classloader.ServiceApiImpl";
    private static final String BASE_DIR = "data/";
    private static final String FILE_NAME = BASE_DIR +
                CLASS_NAME.replaceAll("\\.", "/") + ".class";
    private static volatile ServiceApi serviceApi;


    public static ServiceApi getService() {
        if (serviceApi == null) {
            synchronized (HotDeployDemo.class) {
                if (serviceApi == null) {
                    serviceApi = createService();
                }
            }
        }
        return serviceApi;
    }

    private static ServiceApi createService() {
        ServiceApi serviceApi = null;
        ClassLoader classLoader = new OwnClassLoader();
        try {
            Class<?> klass = classLoader.loadClass(CLASS_NAME);
            serviceApi = (ServiceApi) klass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceApi;
    }

    private static void reloadService() {
        serviceApi = createService();
    }

    public static void client() {
        Thread t = new Thread() {
            @Override
            public void run(){
                while (true) {
                    try {
                        System.out.println("client running thread=" + Thread.currentThread().getName() );
                        ServiceApi serviceApi = getService();
                        serviceApi.action();
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public static void monitor() {
        Thread t = new Thread() {
            private long lastModifier = new File(FILE_NAME).lastModified();
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                        System.out.println("monitor running thread=" + Thread.currentThread().getName() );
                        long now = new File(FILE_NAME).lastModified();
                        if (now != lastModifier) {
                            reloadService();
                            lastModifier = now;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();
    }

    public static void main(String[] args) {
        client();
        monitor();
    }
}
