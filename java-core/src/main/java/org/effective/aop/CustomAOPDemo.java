package org.effective.aop;

public class CustomAOPDemo {
    public static void main(String[] args) {
        ServiceA serviceA = CGLibContainer.getInstance(ServiceA.class);
        serviceA.hello();;
    }
}
