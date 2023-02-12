package com.demo.javademo.concurrency.future.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author：marco.pan
 * @ClassName：FutureThenRunTest
 * @Description：
 * @date: 2022年01月19日 2:09 下午
 * <p>
 * CompletableFuture的thenRun方法，通俗点讲就是，做完第一个任务后，再做第二个任务。
 * 某个任务执行完成后，执行回调方法；但是前后两个任务没有参数传递，第二个任务也没有返回值
 */
@Slf4j
public class FutureThenRunTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("FutureThenRunTest start");
        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("先执行第一个CompletableFuture方法任务");
                    return "捡田螺的小男孩";
                }
        );

        CompletableFuture<Void> thenRunFuture = orgFuture.thenRun(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("接着执行第二个任务");
        });
        log.info("orgFuture.get: {}", orgFuture.join());
        while (!thenRunFuture.isDone());
        log.info("thenRunFuture.get: {}", thenRunFuture.get());
    }
}
