package com.demo.javademo.limitTraffic;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author：marco.pan
 * @ClassName：SlidingTimeWindow
 * @Description：滑动窗口
 * @date: 2022年04月16日 14:05
 */
public class SlidingTimeWindow {
    // 服务访问次数，可以放在Redis中，实现分布式系统的访问计数
    Long counter = 0L;
    // 使用LinkedList来记录滑动窗口的10个格子。
    LinkedList<Long> slots = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        SlidingTimeWindow timeWindow = new SlidingTimeWindow();
        new Thread(() -> {
            try {
                timeWindow.doCheck();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            timeWindow.counter++;
            Thread.sleep(new Random().nextInt(15));
        }
    }

    private void doCheck() throws InterruptedException {
        while (true) {
            slots.addLast(counter);
            if (slots.size() > 10) {
                slots.removeFirst();
            }
            //比较最后一个和第一个，两者相差100以上就限流

            if ((slots.peekLast() - slots.peekFirst()) > 100) {
                System.out.println(" 限流了。。 ");
                //TODO:修改限流标记为true

            } else {
                //TODO:修改限流标记为false
            }
            Thread.sleep(100);
        }
    }
}
