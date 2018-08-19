package org.effective.aop;

import org.effective.di.SimpleInject;

public class ServiceA {
    @SimpleInject
    private ServiceB serviceB;

    public void hello() {
        serviceB.hello();
    }
}