package com.jep.github.interview.concurrency.lock.condition;

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

    static class ItemQueue {
        private Object[] items = null;
        private int current = 0;
        private int placeIndex = 0;
        private int removeIndex = 0;
        private final Lock lock;
        private final Condition isEmpty;
        private final Condition isFull;

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
                isFull.await();
            }
            items[placeIndex] = item;
            placeIndex = (placeIndex + 1) % items.length;
            ++current;
//唤醒等待的线程
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
                isEmpty.await();
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