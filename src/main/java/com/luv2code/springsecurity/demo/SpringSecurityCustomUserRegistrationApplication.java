package com.luv2code.springsecurity.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityCustomUserRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityCustomUserRegistrationApplication.class, args);
	}

}
