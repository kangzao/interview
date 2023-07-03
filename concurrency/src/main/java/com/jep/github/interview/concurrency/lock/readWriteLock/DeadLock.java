package com.jep.github.interview.concurrency.lock.readWriteLock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author enping
 * @date 2023/6/19 16:59
 **/
public class DeadLock {

    public static void main(String args[]) {
        ReadWriteLock rtLock = new ReentrantReadWriteLock();
        rtLock.writeLock().lock();
        System.out.println("writeLock");
        rtLock.readLock().lock();
        System.out.println("get read lock");
    }
}
