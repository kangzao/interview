package com.jep.github.interview.concurrency.lock.aqs.semaphore;

import com.jep.github.interview.concurrency.lock.aqs.MyAqs;

// 自定义的信号量实现
public class NeteaseSemaphore {
    MyAqs aqs = new MyAqs() {
        @Override
        public int tryAcquireShared() { // 信号量获取， 数量 - 1
            for (; ; ) {
                int count = getState().get();
                int n = count - 1;
                if (count <= 0 || n < 0) {
                    return -1;
                }
                if (getState().compareAndSet(count, n)) {
                    return 1;
                }
            }
        }

        @Override
        public boolean tryReleaseShared() { // state + 1
            return getState().incrementAndGet() >= 0;
        }
    };

    /**
     * 许可数量
     */
    public NeteaseSemaphore(int count) {
        aqs.getState().set(count); // 设置资源的状态
    }

    public void acquire() {
        aqs.acquireShared();
    } // 获取令牌

    public void release() {
        aqs.releaseShared();
    } // 释放令牌
}