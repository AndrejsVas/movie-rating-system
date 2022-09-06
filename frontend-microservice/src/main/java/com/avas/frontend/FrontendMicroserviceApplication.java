package com.avas.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.avas.frontend.*", "com.avas.library.*"})
@EntityScan({"com.avas.*", "com.avas.library.*"})
public class FrontendMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendMicroserviceApplication.class, args);
	}

}
