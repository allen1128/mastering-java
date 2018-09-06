package org.effective.di;

public class ServiceA {
    @SimpleInject
    private ServiceB serviceB;

    public void hello() {
        serviceB.hello();
    }
}