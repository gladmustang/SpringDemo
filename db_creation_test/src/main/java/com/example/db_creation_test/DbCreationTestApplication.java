package com.example.db_creation_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DbCreationTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbCreationTestApplication.class, args);
    }

}
