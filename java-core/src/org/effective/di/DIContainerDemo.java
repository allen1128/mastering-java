package org.effective.di;

public class DIContainerDemo {
    public static void main(String[] args) {
        ServiceA serviceA = SimpleContainer.getInstance(ServiceA.class);
        serviceA.hello();
    }
}
