package com.easypeach.shroopadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShroopadminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShroopadminApplication.class, args);
	}

}
