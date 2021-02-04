package com.example.hbnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HbntApplication {


    public static void main(String[] args) {
        SpringApplication.run(HbntApplication.class, args);
    }

}
