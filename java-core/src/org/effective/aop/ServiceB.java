package org.effective.aop;

public class ServiceB {
    public String action(String args){
        String result = "action from B with args=" + args;
        return result;
    }
}
