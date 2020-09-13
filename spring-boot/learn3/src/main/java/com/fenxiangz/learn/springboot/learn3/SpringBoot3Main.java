package com.fenxiangz.learn.springboot.learn3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// 启动时增加 --debug 参数，可以查看到 auto-configuration 的详细信息
@SpringBootApplication
public class SpringBoot3Main {

    private static final Logger log = LoggerFactory.getLogger(SpringBoot3Main.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot3Main.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Quote quote = restTemplate.getForObject(
                    "https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            log.info("========= \n" + quote.toString());
            log.info("========= ");
        };
    }
}
