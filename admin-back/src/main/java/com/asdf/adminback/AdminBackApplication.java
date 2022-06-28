package com.asdf.adminback;

import com.asdf.adminback.models.IpAddress;
import com.asdf.adminback.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EnableMongoRepositories(basePackages = "com.asdf.adminback.repositories.mongo")
@SpringBootApplication
public class AdminBackApplication {

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

	@Bean
	public List<IpAddress> loadMaliciousIpAddresses() {

		File file = new File("src/main/resources/malicious_ip_address.txt");
		BufferedReader br = null;
		List<IpAddress> ipAddresses = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(file));

			String str;
			while ((str = br.readLine()) != null)
				ipAddresses.add(new IpAddress(str));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ipAddresses;
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminBackApplication.class, args);
	}

}
