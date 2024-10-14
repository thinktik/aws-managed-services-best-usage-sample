package com.example.awsrdsmysqldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.awsrdsmysqldemo.daos")
public class AwsRdsMysqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsRdsMysqlDemoApplication.class, args);
    }

}
