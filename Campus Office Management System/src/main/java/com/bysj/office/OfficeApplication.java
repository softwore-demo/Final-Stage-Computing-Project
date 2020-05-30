package com.bysj.office;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.bysj.office.*.mapper")
public class OfficeApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OfficeApplication.class).run(args);
    }

}
