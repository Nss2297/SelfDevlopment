package com.ezybytes.demo_spring_security.service;

import com.ezybytes.demo_spring_security.model.Customer;
import org.springframework.http.ResponseEntity;

public interface UserService {
	ResponseEntity<String> registerUser(Customer customer);
}
