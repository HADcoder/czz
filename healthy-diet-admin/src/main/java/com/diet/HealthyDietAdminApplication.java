package com.diet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author LiuYu
 */
@ServletComponentScan
@EnableCaching
@SpringBootApplication
public class HealthyDietAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthyDietAdminApplication.class, args);
	}
}
