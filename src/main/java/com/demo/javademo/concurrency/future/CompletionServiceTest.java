package com.demo.javademo.concurrency.future;

import java.util.concurrent.*;

/**
 * @author：marco.pan
 * @ClassName：CompletionServiceTest
 * @Description：
 * @date: 2022年04月30日 01:44
 */
public class CompletionServiceTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executor);
        final int groupNum = 100000000 / 100;
        for (int i = 1; i <= 100; i++) {
            int start = (i - 1) * groupNum, end = i * groupNum;
            completionService.submit(() -> {
                Long sum = 0L;
                for (int j = start; j < end; j++) {
                    sum += j;
                }
                return sum;
            });
        }
        Long result = 0L;
        try {
            for (int i = 0; i < 100; i++) {
                result += completionService.take().get();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
