package com.ezybytes.demo_spring_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//In case if the repositories and model are not under the main package
//@EnableJpaRepositories("com.eazybytes.repository")
//@EntityScan("com.eazybytes.model")
public class EasyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBankBackendApplication.class, args);
	}

}
