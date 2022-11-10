package com.comfihealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.comfihealth.security.authentication",
                "com.comfihealth.amqp"
        }
)
public class ComfidentialityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComfidentialityApplication.class, args);
    }
}