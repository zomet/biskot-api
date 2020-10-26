package com.biskot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;

@SpringBootApplication(scanBasePackages = "com.biskot")
public class BiskotApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BiskotApiApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
		app.run(args);
	}

}
