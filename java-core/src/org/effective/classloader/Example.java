package org.effective.classloader;

public class Example {
    public static void main(String[] args) {
        Caller caller = new Caller();
        caller.call();
    }
    public static class Caller{
        public void call() {
            Callee callee = new Callee();
            callee.resp();
        }
    }
    public static class Callee {
        public void resp() {
            int i = 0;
        }
    }
}
