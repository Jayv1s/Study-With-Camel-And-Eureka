package com.example.home.ApacheCamelEureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringApplication {

    public static void main(final String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }
}
