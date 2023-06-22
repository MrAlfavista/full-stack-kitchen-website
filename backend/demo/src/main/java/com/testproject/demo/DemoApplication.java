package com.testproject.demo;

import com.testproject.demo.repository.IngredientRepository;
import com.testproject.demo.service.IngredientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
