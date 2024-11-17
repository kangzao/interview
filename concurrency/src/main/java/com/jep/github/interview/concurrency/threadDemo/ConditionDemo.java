package com.jep.github.interview.concurrency.threadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        ItemQueue itemQueue = new ItemQueue(10);
        //生产者、消费者
        Thread producer = new Producer(itemQueue);
        Thread consumer = new Consumer(itemQueue);
        //启动生产、消费线程
        producer.start();
        consumer.start();
    }

    /**
     * ItemQueue 类是一个自定义的线程安全队列，用于在多线程环境中存储和传递对象。
     * 它使用条件变量来控制队列的填充和移除操作，确保在队列为空或满时线程能够正确地等待和通知。
     */
    static class ItemQueue {
        // 存储队列中项目的数组
        private Object[] items = null;
        // 当前队列中的项目数
        private int current = 0;
        // 下一个要放置项目的索引
        private int placeIndex = 0;
        // 下一个要移除项目的索引
        private int removeIndex = 0;
        // 锁对象，用于同步访问队列资源
        private final Lock lock;
        // 空队列条件，用于通知队列已空
        private final Condition isEmpty;
        // 满队列条件，用于通知队列已满
        private final Condition isFull;

        /**
         * 构造一个指定容量的 ItemQueue 实例。
         *
         * @param capacity 队列的容量，即最多能存储的项目数。
         */
        public ItemQueue(int capacity) {
            this.items = new Object[capacity];
            lock = new ReentrantLock();
            isEmpty = lock.newCondition();
            isFull = lock.newCondition();
        }

        /**
         * 向队列中添加元素，如果队列满，则等待
         *
         * @param item
         * @throws InterruptedException
         */
        public void add(Object item) throws InterruptedException {
            // 获取锁
            lock.lock();
            // 条件判断
            while (current >= items.length) {
                // 条件不满足，等待
                isFull.await();//wait();  notify（） synchronized wait 此线程进入waitset队列   等待被notify
            }
            items[placeIndex] = item;
            placeIndex = (placeIndex + 1) % items.length;
            ++current;//放了几个元素
            //唤醒等待的线程  通知消费者消费
            isEmpty.signalAll();
            // 释放锁
            lock.unlock();
        }

        /**
         * 消费队列中的元素，如果队列为空，等待
         *
         * @return
         * @throws InterruptedException
         */
        public Object remove() throws InterruptedException {
            Object item = null;
            lock.lock();
            while (current <= 0) {
                isEmpty.await();//wait方法
            }
            item = items[removeIndex];
            removeIndex = (removeIndex + 1) % items.length;
            --current;
            isFull.signal();
            lock.unlock();
            return item;
        }

        public boolean isEmpty() {
            return (items.length == 0);
        }
    }

    static class Producer extends Thread {
        private final ItemQueue queue;

        public Producer(ItemQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            String[] numbers =
                    {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            try {
                for (String number : numbers) {
                    System.out.println("[Producer]: " + number);
                    queue.add(number);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {
        private final ItemQueue queue;

        public Consumer(ItemQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                do {
                    Object number = queue.remove();
                    System.out.println("[Consumer]: " + number);
                    if (number == null) {
                        return;
                    }
                } while (!queue.isEmpty());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}