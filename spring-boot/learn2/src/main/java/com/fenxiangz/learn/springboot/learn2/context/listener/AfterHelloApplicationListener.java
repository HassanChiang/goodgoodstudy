package com.fenxiangz.learn.springboot.learn2.context.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class AfterHelloApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("========= After HelloApplicationListener.onApplicationEvent start");
        System.out.println(event.getApplicationContext().getId());
        System.out.println(event.getTimestamp());
        System.out.println(event.getSource());
        System.out.println("========= After HelloApplicationListener.onApplicationEvent end");
    }
}
