package com.bideris.dbservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaRepositories(basePackages = "com.bideris.dbservice.repository")
@SpringBootApplication
public class DbServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(DbServiceApplication.class, args);

	}

}

