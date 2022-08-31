package com.avas.movietype.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"main.java.com.avas.library"})
public class MovieTypeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTypeMicroserviceApplication.class, args);
	}

}
