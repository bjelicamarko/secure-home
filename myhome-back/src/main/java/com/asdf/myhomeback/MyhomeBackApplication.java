package com.asdf.myhomeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableMongoRepositories(basePackages = "com.asdf.myhomeback.repositories.mongo")
@EnableScheduling
@SpringBootApplication
public class MyhomeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyhomeBackApplication.class, args);
	}

}
