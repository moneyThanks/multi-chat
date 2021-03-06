package com.jc.multiplechatapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jc")
public class MultipleChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleChatApiApplication.class, args);
    }

}
