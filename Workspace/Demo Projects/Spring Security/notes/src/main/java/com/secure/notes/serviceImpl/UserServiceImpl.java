package com.secure.notes.serviceImpl;

import com.secure.notes.dto.UserDTO;
import com.secure.notes.entity.AppRole;
import com.secure.notes.entity.Role;
import com.secure.notes.entity.User;
import com.secure.notes.repository.RoleRepository;
import com.secure.notes.repository.UserRepository;
import com.secure.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<User> fetchAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String modifyUserRole(Long userId, String userRole) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Invalid user"));
		AppRole appRole = AppRole.valueOf(userRole);
		Role role = roleRepository.findByRoleName(appRole)
				.orElseThrow(() -> new RuntimeException("Invalid user role."));
		user.setRole(role);
		userRepository.save(user);
		return "User Role modified suceessfully.";
	}

	@Override
	public UserDTO fetchUserDetails(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Invalid user"));
		return mapWithUserDTO(user);
	}

	private UserDTO mapWithUserDTO(User user) {
		return new UserDTO(user.getUserId(), user.getUserName(), user.getEmail(), user.isAccountNonLocked(),
				user.isAccountNonExpired(), user.isCredentialNonExpired(), user.isEnabled(),
				user.getCredentialsExpiryDate(), user.getAccountExpiryDate(), user.getTwoFactorSecret(),
				user.isTwoFactorEnabled(), user.getSignUpMethod(), user.getRole(), user.getCreateDate(),
				user.getUpdateDate());
	}
}
