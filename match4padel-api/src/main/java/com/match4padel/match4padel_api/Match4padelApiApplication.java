package com.match4padel.match4padel_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Match4padelApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Match4padelApiApplication.class, args);
	}

}
