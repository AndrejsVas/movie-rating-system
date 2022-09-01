package com.avas.user.like.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.avas.user.like.*", "com.avas.library.*"})
@EntityScan({"com.avas.*", "com.avas.library.*"})
public class UserLikeMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserLikeMicroserviceApplication.class, args);
	}

}
