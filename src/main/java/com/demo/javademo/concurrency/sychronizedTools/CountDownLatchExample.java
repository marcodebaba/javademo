package com.demo.javademo.concurrency.sychronizedTools;

/**
 * @author：marco.pan
 * @ClassName：CountDownLatchExample
 * @Description：
 * @date: 2023年02月12日 21:40
 */
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {
    static List<BaseHealthChecker> healthCheckers = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        healthCheckers.add(new ApplicationHealthChecker(countDownLatch, "Application"));
        healthCheckers.add(new NetworkHealthChecker(countDownLatch, "Network"));

        try {
            healthCheckers.stream().forEach(healthChecker -> {
                Thread t = new Thread(healthChecker);
                t.start();
            });
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("All Services are fine");
    }
}

@Getter
abstract class BaseHealthChecker implements Runnable {
    private CountDownLatch countDownLatch;
    private String serviceName;
    private boolean serviceUp = false;

    public BaseHealthChecker(CountDownLatch countDownLatch, String serviceName) {
        this.countDownLatch = countDownLatch;
        this.serviceName = serviceName;
    }

    @Override
    public void run() {
        try {
            verifyService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void verifyService() throws Exception;
}

class NetworkHealthChecker extends BaseHealthChecker {
    public NetworkHealthChecker(CountDownLatch countDownLatch, String serviceName) {
        super(countDownLatch, serviceName);
    }

    @Override
    public void verifyService() throws Exception {
        try {
            System.out.println("Checking Network Service health");
            TimeUnit.MILLISECONDS.sleep(200);
            System.out.println(getServiceName() + " Health is fine...");
        } finally {
            getCountDownLatch().countDown();
        }
    }
}

class ApplicationHealthChecker extends BaseHealthChecker {
    public ApplicationHealthChecker(CountDownLatch countDownLatch, String serviceName) {
        super(countDownLatch, serviceName);
    }

    @Override
    public void verifyService() throws Exception {
        try {
            System.out.println("Checking Application Service health");
            /**TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(getServiceName() + " Health is fine...");*/
            throw new Exception("ApplicationHealthChecker exception");
        } finally {
            getCountDownLatch().countDown();
        }
    }
}
