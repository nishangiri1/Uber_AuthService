package com.auth.uber_authservice;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.entity.uberprojectentityservice.models")
public class UberAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberAuthServiceApplication.class, args);
    }

}
