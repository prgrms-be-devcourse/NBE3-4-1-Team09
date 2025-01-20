package com.nbe3.cafemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class CafemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafemanagementApplication.class, args);
    }
}