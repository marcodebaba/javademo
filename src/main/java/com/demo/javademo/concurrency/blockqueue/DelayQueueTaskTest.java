package com.demo.javademo.concurrency.blockqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author：marco.pan
 * @ClassName：DelayQueueTaskTest
 * @Description：
 * @date: 2023年02月12日 16:39
 */
@Slf4j
public class DelayQueueTaskTest {
    private static DelayQueue<DelayQueueTask> delayQueue = new DelayQueue();

    public static void main(String[] args) {
        delayQueue.add(new DelayQueueTask("1001", 1000));
        delayQueue.add(new DelayQueueTask("1002", 5000));
        delayQueue.add(new DelayQueueTask("1003", 3000));
        delayQueue.add(new DelayQueueTask("1004", 2000));
        delayQueue.add(new DelayQueueTask("1005", 4000));
        while (true) {
            try {
                DelayQueueTask delayed = delayQueue.take();
                System.out.println(delayed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DelayQueueTask implements Delayed {
    private String orderId;
    private long start = System.currentTimeMillis();
    private long time;

    @Override
    public String toString() {
        return "DelayQueueTask{" +
                "orderId='" + orderId + '\'' +
                ", time=" + time +
                '}';
    }

    public DelayQueueTask(String orderId, long time) {
        this.orderId = orderId;
        this.time = time;
    }


    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(start + time - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int)(this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
