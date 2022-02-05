package com.example.juc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.juc.mapper")
public class JucApplication {

    public static void main(String[] args) {
        SpringApplication.run(JucApplication.class, args);
    }

}
