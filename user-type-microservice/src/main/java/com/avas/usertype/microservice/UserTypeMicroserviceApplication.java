package com.avas.usertype.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"main.java.com.avas.library"})
public class UserTypeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserTypeMicroserviceApplication.class, args);
	}

}
