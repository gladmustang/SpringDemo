package com.pwc.faast.notesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class DbSwitchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbSwitchDemoApplication.class, args);
    }

}
