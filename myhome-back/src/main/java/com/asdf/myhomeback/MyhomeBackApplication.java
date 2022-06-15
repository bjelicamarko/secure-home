package com.asdf.myhomeback;

import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.impls.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@EnableMongoRepositories(basePackages = "com.asdf.myhomeback.repositories.mongo")
@EnableScheduling
@SpringBootApplication
public class MyhomeBackApplication {

	@Component
	public static class ShutdownEvent {

		@Autowired
		private LogService logService;

		@PreDestroy
		public void shutDownCallback() {
			logService.generateFatalLog("Spring app is forced to shut down.");
		}

		@EventListener(ApplicationReadyEvent.class)
		public void startUpCallback() {
			logService.generateInfoLog("Spring app is started up.");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MyhomeBackApplication.class, args);
	}

}
