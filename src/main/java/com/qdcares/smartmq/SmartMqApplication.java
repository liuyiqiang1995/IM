package com.qdcares.smartmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMqApplication.class, args);
	}

}
