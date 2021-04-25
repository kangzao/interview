package com.jep.github.interview.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author enping
 * @date 2021/4/23 下午7:10
 * 自己实现独享锁
 **/
public class MyLock implements Lock {
    //判断一个状态或者拥有者
    AtomicReference<Thread> owner = new AtomicReference<>();
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        boolean addQueue = true;
        while (!tryLock()) {
            if (addQueue) {
                //没拿到锁，加入到等待集合
                waiters.offer(Thread.currentThread());
                addQueue = false;
            } else {
                //阻塞,挂起当前的线程，不继续往下执行
                LockSupport.park();//伪唤醒
            }
        }
        //拿到锁之后，从等待的集合中去掉
        waiters.remove(Thread.currentThread());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return owner.compareAndSet(null, Thread.currentThread());
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        //释放owner
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            //通知等待者
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
