package com.demo.javademo.concurrency.sychronizedTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        // 令牌数
        Semaphore toilet = new Semaphore(3);
        for (int i = 1; i <= 10; i++) {
            Person person = new Person("第" + i + "个人,", toilet);
            //new Thread(person).start();
            executorService.execute(person);
        }
        executorService.shutdown();
    }

    static class Person implements Runnable {
        private String name;
        private Semaphore toilet;

        public Person(String name, Semaphore toilet) {
            this.name = name;
            this.toilet = toilet;
        }

        @Override
        public void run() {
            try {
                // 剩下的资源：即剩下的茅坑
                if (toilet.availablePermits() > 0) {
                    System.out.println(name + "天助我也,终于有茅坑了...");
                } else {
                    System.out.println(name + "怎么没有茅坑了...");
                }
                // 申请茅坑，如果资源达到3次就等待
                // 申请令牌，拿到令牌就可以往下跑
                toilet.acquire();
                System.out.println(name + "终于轮我上厕所了..爽啊");
                // 模拟上厕所时间。
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + "厕所上完了...");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 释放令牌
                toilet.release();
            }
        }
    }
}