package com.fenxiangz.learn.springboot.learn1.selector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String helloWorld() {
        return "helloWorld";
    }

    @Bean
    public TestBean testBean() {
        return new TestBean();
    }
}
