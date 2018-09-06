package org.effective.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class FutureTaskDemo {
    public static class MyFutureThread implements Callable {
        @Override
        public Object call() throws Exception {
            sleep((long) (Math.random()*1000));
            return new Integer(3);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future task = service.submit(new MyFutureThread());
        sleep((long) (Math.random()*1000));
        task.cancel(true);
        Integer i  = (Integer) task.get();
        System.out.println("number="+i);
    }
}
