package com.projectathena.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AthenaUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AthenaUserServiceApplication.class, args);
    }

}
