package org.borg.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class DistributedRedisLock implements Lock{

    public DistributedRedisLock() {
    }

    @Override
    public void lock() {
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
