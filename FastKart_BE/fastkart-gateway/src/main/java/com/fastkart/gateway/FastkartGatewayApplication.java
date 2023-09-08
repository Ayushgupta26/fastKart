package com.fastkart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FastkartGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastkartGatewayApplication.class, args);
	}

}
