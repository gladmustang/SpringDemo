package com.example.multiple_db_demo;

import com.example.multiple_db_demo.config.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(DynamicDataSourceRegister.class)
@SpringBootApplication
public class MultipleDbDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDbDemoApplication.class, args);
    }

}
