package com.easypeach.shroop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShroopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShroopApplication.class, args);
	}

}
