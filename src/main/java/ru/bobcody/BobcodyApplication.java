package ru.bobcody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableConfigurationProperties
@SpringBootApplication
public class BobcodyApplication {
    public static void main(String[] args) {
        SpringApplication.run(BobcodyApplication.class, args);
    }
}
