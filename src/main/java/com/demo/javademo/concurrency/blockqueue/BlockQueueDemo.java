package com.demo.javademo.concurrency.blockqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockQueueDemo<E> {
    private int count;
    private LinkedList<E> items = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    // 初始化BlockQueue
    public BlockQueueDemo(int count) {
        this.count = count;
    }

    public static void main(String[] args) {
        BlockQueueDemo<Integer> blockQueue = new BlockQueueDemo<>(20);

        new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                blockQueue.enQueue(index);
            }
        }, "enQueue").start();

        new Thread(() -> {
            for (int index = 0; index < 100; index++) {
                blockQueue.deQueue();
            }
        }, "deQueue").start();
    }

    /**
     * 入队操作，队列满了,入队要阻塞(condition的await)
     *
     * @param e
     */
    public void enQueue(E e) {
        //非空判断
        if (e == null) throw new NullPointerException();

        // 支持多线程，得要加锁
        lock.lock();
        try {
            // 队列满了，入队阻塞
            while (items.size() == count) {
                log.info("阻塞队列满了");
                notFull.await();
            }
            // 否则将元素入队
            items.add(e);
            log.info(e + "元素入队了");

            // 通知可以取元素了
            notEmpty.signal();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出列操作，如果队列空了，出队要阻塞(condition的await)
     *
     * @return
     */
    public E deQueue() {
        E element = null;
        lock.lock();
        try {
            // 队列空，出队阻塞
            while (items.isEmpty()) {
                log.info("阻塞队列空了");
                notEmpty.await();
            }
            // 否则将元素出队
            element = items.removeFirst();
            log.info(element + "元素出列了");

            // 通知可以取元素了
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return element;
    }
}
