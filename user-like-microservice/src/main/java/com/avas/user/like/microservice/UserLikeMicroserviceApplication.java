package com.avas.user.like.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"main.java.com.avas.library"})
public class UserLikeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLikeMicroserviceApplication.class, args);
	}

}
