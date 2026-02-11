package com.demo.javademo.jvm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author：marco.pan
 * @ClassName：MethodReplaceExample
 * @Description：
 * @date: 2026年02月11日 13:30
 */
@Slf4j
public class MethodReplaceExample {

    // 原始类
    static class Calculator {
        public int add(int a, int b) {
            // 原始逻辑
            return a + b;
        }

        public double getPrice() {
            return 100.0;  // 原价
        }

        public double calculateDiscount(double price) {
            // Bug: 应该是 0.9 折
            return  price * 0.1;
        }
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Calculator.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {

            switch (method.getName()) {
                case "add":
                    // 完全替换原有逻辑，不调用原方法
                    int a = (int) args1[0];
                    int b = (int) args1[1];
                    int result = a * b;  // 改成乘法！
                    log.info("逻辑已被替换: {} * {} = {}", a, b, result);
                    return result;  // 不调用 invokeSuper，直接返回

                case "getPrice":    // 修改返回值：打8折
                    double originalPrice = (double) proxy.invokeSuper(obj, args);
                    double discountedPrice = originalPrice * 0.8;
                    log.info("价格修改: {} -> {}", originalPrice, discountedPrice);
                    return discountedPrice;
                case "calculateDiscount":   // 修复 Bug：不调用原方法，直接用正确逻辑
                    double price = (double) args1[0];
                    double errorResult = (double) proxy.invokeSuper(obj, new Object[]{price});
                    // 修正后的逻辑
                    double correctResult = price * 0.9;
                    log.info("[热修复] Bug 已修复: {} -> {}", errorResult, correctResult);
                    return correctResult;
            }

            return proxy.invokeSuper(obj, args1);
        });

        Calculator proxy = (Calculator) enhancer.create();

        int result = proxy.add(5, 3);
        log.info("结果: result = {}", result);

        double proxyPrice = proxy.getPrice();
        log.info("结果: proxyPrice = {}", proxyPrice);

        double fixedDiscount = proxy.calculateDiscount(1000);
        log.info("结果: fixedDiscount = {}", fixedDiscount);
    }


}

/* 输出：
逻辑已被替换: 5 * 3 = 15
结果: 15
*/
