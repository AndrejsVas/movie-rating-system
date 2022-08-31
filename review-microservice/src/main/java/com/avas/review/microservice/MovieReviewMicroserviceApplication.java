package com.avas.review.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"main.java.com.avas.library"})
public class MovieReviewMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieReviewMicroserviceApplication.class, args);
	}

}
