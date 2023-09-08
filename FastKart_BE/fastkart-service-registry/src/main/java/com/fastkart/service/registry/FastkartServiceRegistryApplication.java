package com.fastkart.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FastkartServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastkartServiceRegistryApplication.class, args);
	}

}
