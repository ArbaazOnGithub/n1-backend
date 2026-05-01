package com.n1solution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class N1SolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(N1SolutionApplication.class, args);
	}

}
