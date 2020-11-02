package com.kenon.kenonapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KenonappApplication {

    public static void main(String[] args) {
        SpringApplication.run(KenonappApplication.class, args);
    }

}
