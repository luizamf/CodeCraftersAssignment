package com.challenge.clientEnroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories("com.challenge.clientEnroller.repository")
public class ClientEnrollApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientEnrollApplication.class, args);
    }

}
