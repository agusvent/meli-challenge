package com.agustinventura.melichallenge.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CacheApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CacheApplication.class, args);
	}

}
