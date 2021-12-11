package com.ezil.reminder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;

@SpringBootApplication()
@MapperScan("com.ezil.reminder.mapper")
public class EzilReminderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EzilReminderApplication.class,args);
    }
}
