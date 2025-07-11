package com.cryotunning.cryotunning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CryoTuningApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryoTuningApplication.class, args);
	}

}
