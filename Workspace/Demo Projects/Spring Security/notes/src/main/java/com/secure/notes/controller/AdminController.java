package com.secure.notes.controller;

import com.secure.notes.dto.UserDTO;
import com.secure.notes.entity.User;
import com.secure.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final UserService userService;

	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
	}

	@PutMapping("/update-role/{userId}")
	ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String userRole) {
		return new ResponseEntity<>(userService.modifyUserRole(userId, userRole), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	ResponseEntity<UserDTO> findUserDetails(@PathVariable Long userId) {
		return new ResponseEntity<>(userService.fetchUserDetails(userId), HttpStatus.OK);
	}
}
