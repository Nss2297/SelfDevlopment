package com.ezybytes.demo_spring_security.service;

import com.ezybytes.demo_spring_security.model.Customer;
import com.ezybytes.demo_spring_security.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private PasswordEncoder passwordEncoder;
	private CustomerRepository customerRepository;

	@Autowired
	public UserServiceImpl(PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
		this.passwordEncoder = passwordEncoder;
		this.customerRepository = customerRepository;
	}

	@Override
	public ResponseEntity<String> registerUser(Customer customer) {
		try {
			String encodedPassword = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(encodedPassword);
			customer = customerRepository.save(customer);
			if (customer.getId() > 0) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully created.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
			}
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception occurred: " + exception.getMessage());
		}
	}
}
