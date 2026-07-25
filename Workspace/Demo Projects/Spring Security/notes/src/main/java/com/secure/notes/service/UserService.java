package com.secure.notes.service;

import com.secure.notes.dto.UserDTO;
import com.secure.notes.entity.User;

import java.util.List;

public interface UserService {
	List<User> fetchAllUsers();

	String modifyUserRole(Long userId, String userRole);

	UserDTO fetchUserDetails(Long userId);
}
