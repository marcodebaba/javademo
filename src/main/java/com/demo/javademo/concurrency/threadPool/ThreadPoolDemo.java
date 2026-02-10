package com.demo.javademo.concurrency.threadPool;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        // 一批批的跑
        ExecutorService executorService1 = Executors.newFixedThreadPool(10);//慢
        // 所有的一起跑
        ExecutorService executorService2 = Executors.newCachedThreadPool();//快
        // 一个个地跑
        ExecutorService executorService3 = Executors.newSingleThreadExecutor();//最慢
        // 自定义线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20,
                0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

        for (int i = 0; i < 100; i++) {
//            executorService1.execute(new MyTask(i));
//            executorService2.execute(new MyTask(i));
            executorService3.execute(new MyTask(i));
            //threadPoolExecutor.execute(new MyTask(i));
        }
    }

    static class MyTask implements Runnable {
        int i;

        public MyTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "--" + i);
            try {
                Thread.sleep(1000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
