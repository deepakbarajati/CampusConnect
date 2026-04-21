package com.campusConnect.aluminiService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AluminiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AluminiServiceApplication.class, args);
	}

}
