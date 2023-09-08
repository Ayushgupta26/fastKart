package com.pip.application_gateway;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {



	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}