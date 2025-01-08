package com.example.hivemindbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HivemindbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HivemindbackendApplication.class, args);
	}

}
