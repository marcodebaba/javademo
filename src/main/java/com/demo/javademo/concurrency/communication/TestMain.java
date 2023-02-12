package com.demo.javademo.concurrency.communication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        Queue<String> queue = new LinkedList<>();
        Producer producer = new Producer(queue, 5);
        Consumer consumer = new Consumer(queue);
        Thread t1 = new Thread(producer, "producer");
        Thread t2 = new Thread(consumer, "consumer");
        t1.start();
        TimeUnit.MILLISECONDS.sleep(100);
        t2.start();
    }
}
