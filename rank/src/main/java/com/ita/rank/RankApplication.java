package com.ita.rank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
@MapperScan("com.wca.rank.dao")
public class RankApplication {

    public static void main(String[] args) {
        SpringApplication.run(RankApplication.class, args);
    }
}
