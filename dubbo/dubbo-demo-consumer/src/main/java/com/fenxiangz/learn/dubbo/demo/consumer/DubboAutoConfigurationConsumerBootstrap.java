package com.fenxiangz.learn.dubbo.demo.consumer;

import com.fenxiangz.learn.dubbo.demo.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Dubbo Auto Configuration Consumer Bootstrap
 *
 * @since 2.7.0
 */
@EnableAutoConfiguration
public class DubboAutoConfigurationConsumerBootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DubboReference(
            version = "1.0.0",
            url = "dubbo://127.0.0.1:12345",
            timeout = 100,
            methods = {
                    @Method(name = "sayHello", timeout = 300)
            }
    )
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(DubboAutoConfigurationConsumerBootstrap.class);
    }

    @Bean
    public ApplicationRunner runner() {
        ApplicationRunner mercyblitz = args -> {
            String mercyblitz1 = demoService.sayHello("mercyblitz");
            logger.info(mercyblitz1);
        };
        return mercyblitz;
    }
}