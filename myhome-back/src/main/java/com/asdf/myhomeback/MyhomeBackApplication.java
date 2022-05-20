package com.asdf.myhomeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyhomeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyhomeBackApplication.class, args);
	}

}
