package com.rollingstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RollingstoneEcommerceEurekaServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RollingstoneEcommerceEurekaServiceDiscoveryApplication.class, args);
	}

}
