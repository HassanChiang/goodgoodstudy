package com.fenxiangz.learn.springboot.learn2.boot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class HelloWorldSpringApplicationRunListener implements SpringApplicationRunListener {
    public HelloWorldSpringApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {
        System.out.println("===== HelloWorldSpringApplicationRunListener starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("===== HelloWorldSpringApplicationRunListener environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("===== HelloWorldSpringApplicationRunListener contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("===== HelloWorldSpringApplicationRunListener contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("===== HelloWorldSpringApplicationRunListener started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("===== HelloWorldSpringApplicationRunListener running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
