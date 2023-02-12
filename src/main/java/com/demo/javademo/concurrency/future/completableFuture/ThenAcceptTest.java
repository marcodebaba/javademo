package com.demo.javademo.concurrency.future.completableFuture;

import io.netty.util.concurrent.CompleteFuture;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author：marco.pan
 * @ClassName：FutureThenAcceptTest
 * @Description：
 * @date: 2022年01月19日 2:13 下午
 * <p>
 * CompletableFuture的thenAccept方法表示，第一个任务执行完成后，执行第二个回调方法任务，会将该任务的执行结果作为入参，
 * 传递到回调方法中，但是回调方法是没有返回值的。两个任务串行行执行，总耗时=total(a,b)，a执行的结果对b可见
 */
@Slf4j
public class ThenAcceptTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        // 不带返回
//        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "Hello World";
//        }).thenAcceptAsync(rs -> System.out.println(rs));
//        System.out.println("main end");
//        while (!completableFuture.isDone());

//        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> "task1");
//        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "task2");
//        CompletableFuture<Void> future = task1.thenAcceptBoth(task2, (result1, result2) -> System.out.println(result1 + ", " + result2));
//        future.get();

        // 带返回
        CompletableFuture<String> applyFuture = CompletableFuture.supplyAsync(() -> "Apply")
                .thenApply(result -> result + ", Mic");
        System.out.println(applyFuture.get());

        CompletableFuture<String> combineFuture = CompletableFuture.supplyAsync(() -> "combine")
                .thenCombineAsync(CompletableFuture.supplyAsync(() -> " message"),
                        (result1, result2) -> result1 + result2);
        System.out.println(combineFuture.get());

//        log.info("FutureThenAcceptTest start");
//
//        CompletableFuture<String> orgFuture = CompletableFuture.supplyAsync(
//                () -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(2);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("原始CompletableFuture方法任务");
//                    return "捡田螺的小男孩";
//                }
//        );
//
//        CompletableFuture<Void> thenAcceptFuture = orgFuture.thenAccept(result -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if ("捡田螺的小男孩".equals(result)) {
//                log.info("关注了{}", result);
//            }
//        });
//        log.info("thenAcceptFuture.get: {}", thenAcceptFuture.get());

//        CompletableFuture<Void> thenAcceptFuture = orgFuture.thenAccept((result) -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if ("捡田螺的小男孩".equals(result)) {
//                log.info("关注了{}", result);
//            }
//            log.info("先考虑考虑");
//        });
//        log.info("thenAcceptFuture.get: {}", thenAcceptFuture.get());
    }
}
