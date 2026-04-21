package com.campusConnect.gamificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class GamificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamificationServiceApplication.class, args);
	}

}
