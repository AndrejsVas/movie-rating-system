package com.avas.movietype.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.avas.*")
@EntityScan("com.avas.*")
public class MovieTypeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTypeMicroserviceApplication.class, args);
	}

}
