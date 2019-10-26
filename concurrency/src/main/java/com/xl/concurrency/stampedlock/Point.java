package com.xl.concurrency.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class Point {
    private int x;
    private int y;
    private final StampedLock lock = new StampedLock();

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceToOrigin() {
        long stamp = lock.tryOptimisticRead();
        int lx;
        int ly;

        if (lock.validate(stamp)) {
            lx = x;
            ly = y;
        } else {
            try {
                stamp = lock.readLock();
                lx = x;
                ly = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return getDistance(lx, ly);
    }

    public double getDistance(int x, int y) {
        return Math.sqrt(x * x + y * y);
    }


    public static void main(String[] args) {
        Point point1 = new Point(2, 3);
        System.out.println(point1.distanceToOrigin());
    }

}
