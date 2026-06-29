package com.hanmin.loafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoafyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoafyApplication.class, args);
    }

}
