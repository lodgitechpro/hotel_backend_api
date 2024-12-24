package com.lodgitechpro.hotelmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class HotelBackendApiApp {

	public static void main(String[] args) {
		SpringApplication.run(HotelBackendApiApp.class, args);
	}

}
