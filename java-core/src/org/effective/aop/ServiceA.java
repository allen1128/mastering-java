package org.effective.aop;

public class ServiceA {

    private ServiceB serviceB;

    public void action() {
        serviceB.action("ABC");
    }
}