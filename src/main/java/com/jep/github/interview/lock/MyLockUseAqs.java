package com.jep.github.interview.lock;

import com.jep.github.interview.lock.aqs.MyAqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author enping
 * @date 2021/4/23 下午9:07
 **/
public class MyLockUseAqs implements Lock {

    // 抽象工具类AQS
    MyAqs aqs = new MyAqs() {
        @Override
        public boolean tryAcquire() {
            return owner.compareAndSet(null, Thread.currentThread());
        }

        @Override
        public boolean tryRelease() {
            // 可重入的情况下，要判断资源的占用情况（state字段保存了资源的占用次数）
            return owner.compareAndSet(Thread.currentThread(), null);
        }
    };


    @Override
    public void lock() {
        aqs.acquire();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return aqs.tryAcquire();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        aqs.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
