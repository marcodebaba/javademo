package com.demo.javademo.concurrency.sychronizedTools;

import java.util.concurrent.CyclicBarrier;

// CyclicBarrier 是共享锁
public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("人到齐了，开会吧...."));

        for (int i = 0; i < cyclicBarrier.getParties(); i++) {
            new CyclicBarrierThread().start();
        }
    }

    static class CyclicBarrierThread extends Thread {
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到了");
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
