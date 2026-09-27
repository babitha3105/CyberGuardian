package com.example.cyberguardian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling

public class CyberGuardianApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyberGuardianApplication.class, args);
    }

}
