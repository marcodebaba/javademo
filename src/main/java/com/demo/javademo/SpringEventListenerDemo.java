package com.demo.javademo;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author：marco.pan
 * @ClassName：SpringEventListenerDemo
 * @Description：Spring事件发布与监听机制
 * @date: 2022年04月19日 22:48
 */
public class SpringEventListenerDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyApplicationListener.class);

        // 发布事件
        context.publishEvent(new MyApplicationEvent("Hello,World"));
        context.publishEvent(new MyApplicationEvent(1));
        context.publishEvent(new MyApplicationEvent(new Double(1.0)));
    }

    @Component
    private static class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
        @Override
        public void onApplicationEvent(MyApplicationEvent event) {
            System.out.printf("MyApplicationListener receives event source : %s \n", event.getSource());
        }
    }


    private static class MyApplicationEvent extends ApplicationEvent {

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }
}
