package com.example.demo.booksservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringApplication {
    public static void main(final String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}


