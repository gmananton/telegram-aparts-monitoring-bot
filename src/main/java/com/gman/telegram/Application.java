package com.gman.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.telegram.telegrambots.ApiContextInitializer;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        log.info("ApiContext initialized");

        SpringApplication.run(Application.class, args);
    }
}
