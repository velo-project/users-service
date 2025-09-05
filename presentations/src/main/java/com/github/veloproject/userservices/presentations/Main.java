package com.github.veloproject.userservices.presentations;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
                "com.github.veloproject.userservices"
})
@EnableCaching
@ComponentScan(basePackages = { "com.github.veloproject.userservices" })
@EntityScan(basePackages = { "com.github.veloproject.userservices.infrastructure.tables" })
@EnableJpaRepositories(basePackages = "com.github.veloproject.userservices.infrastructure.repositories.jpa")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}