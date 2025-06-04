package com.wl.myai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan ("com.wl.myai.mapper")
public class MyAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAiApplication.class, args);
    }

}
