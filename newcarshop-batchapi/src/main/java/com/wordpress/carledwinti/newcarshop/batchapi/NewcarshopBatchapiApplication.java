package com.wordpress.carledwinti.newcarshop.batchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewcarshopBatchapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewcarshopBatchapiApplication.class, args);
	}

}
