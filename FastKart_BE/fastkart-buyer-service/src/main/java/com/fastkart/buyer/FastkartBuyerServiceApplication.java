package com.fastkart.buyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FastkartBuyerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastkartBuyerServiceApplication.class, args);
	}

}
