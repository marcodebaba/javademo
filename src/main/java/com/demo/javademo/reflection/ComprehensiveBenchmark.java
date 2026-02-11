package com.demo.javademo.reflection;

import lombok.Getter;
import lombok.Setter;

import java.lang.invoke.*;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author：marco.pan
 * @ClassName：ComprehensiveBenchmark
 * @Description：Java反射优化
 * @date: 2026年02月11日 12:19
 */
public class ComprehensiveBenchmark {

    @Setter
    @Getter
    static class User {
        private String name;

    }

    public static void main(String[] args) throws Throwable {
        User user = new User();
        user.setName("张三");
        int iterations = 10_000_000;

        // 1. 直接调用（基准）
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            user.getName();
        }
        long directTime = System.nanoTime() - start;

        // 2. 未优化反射
        Method method1 = User.class.getMethod("getName");
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            method1.invoke(user);
        }
        long reflectTime = System.nanoTime() - start;

        // 3. 缓存 + setAccessible
        Method method2 = User.class.getMethod("getName");
        method2.setAccessible(true);
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            method2.invoke(user);
        }
        long optimizedReflect = System.nanoTime() - start;

        // 4. Lambda（最优）
        Function<User, String> getter = createLambdaGetter();
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            getter.apply(user);
        }
        long lambdaTime = System.nanoTime() - start;

        // 输出结果
        System.out.println("直接调用:        " + directTime / 1_000_000 + "ms (基准)");
        System.out.println("未优化反射:      " + reflectTime / 1_000_000 + "ms (" + reflectTime / directTime + "x)");
        System.out.println("优化反射:        " + optimizedReflect / 1_000_000 + "ms (" + optimizedReflect / directTime + "x)");
        System.out.println("Lambda:          " + lambdaTime / 1_000_000 + "ms (" + lambdaTime / directTime + "x)");
    }

    static Function<User, String> createLambdaGetter() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 方法本身的返回类型
        MethodType mt = MethodType.methodType(String.class);
        // 方法的实际签名：String getName()，但作为实例方法，需要 User 作为第一个参数
        MethodHandle mh = lookup.findVirtual(User.class, "getName", mt);

        CallSite site = LambdaMetafactory.metafactory(
                lookup,
                "apply",                                           // Function 接口的方法名
                MethodType.methodType(Function.class),            // 返回 Function 类型
                MethodType.methodType(Object.class, Object.class), // Function.apply 的擦除类型签名: (Object) -> Object
                mh,                                                // 实际要调用的方法
                MethodType.methodType(String.class, User.class)    // 实际类型签名: (User) -> String
        );

        return (Function<User, String>) site.getTarget().invokeExact();
    }
}

/* 典型输出：
直接调用:        5ms (基准)
未优化反射:      34ms (5x)
优化反射:        17ms (3x)
Lambda:          3ms (0x)
*/