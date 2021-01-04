package com.jing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jing.mapper")
@SpringBootApplication
public class NurseHousingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NurseHousingApplication.class, args);
    }

}
