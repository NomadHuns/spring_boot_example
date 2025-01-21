package com.example.spring_boot_jpa_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootJpaExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaExampleApplication.class, args);
    }

}
