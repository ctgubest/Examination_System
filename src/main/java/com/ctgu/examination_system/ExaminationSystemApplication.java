package com.ctgu.examination_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ctgu.examination_system.mapper")
public class ExaminationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExaminationSystemApplication.class, args);
	}
}
