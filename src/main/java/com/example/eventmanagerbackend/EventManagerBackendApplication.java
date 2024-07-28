package com.example.eventmanagerbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EventManagerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagerBackendApplication.class, args);
    }

}
