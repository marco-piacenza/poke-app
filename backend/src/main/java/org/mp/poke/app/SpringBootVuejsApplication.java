package org.mp.poke.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Application entry point
 * 
 * @author Marco Piacenza
 * @version 0.0.1
 * @since 0.0.1
 */
@SpringBootApplication
@EnableCaching
public class SpringBootVuejsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVuejsApplication.class, args);
	}
}
