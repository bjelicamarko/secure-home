package com.asdf.myhomeback;

import com.asdf.myhomeback.models.drools.IpAddress;
import com.asdf.myhomeback.services.LogService;
import com.asdf.myhomeback.services.impls.LogServiceImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
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
		SpringApplication.run(MyhomeBackApplication.class, args);
	}

}
