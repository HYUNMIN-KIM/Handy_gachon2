package com.handysoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@SpringBootApplication
public class HandySoftApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HandySoftApplication.class, args);
	}

	
}
