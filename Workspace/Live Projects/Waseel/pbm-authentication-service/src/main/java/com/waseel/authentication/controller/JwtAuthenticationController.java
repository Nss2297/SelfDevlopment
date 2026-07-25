package com.waseel.authentication.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.waseel.authentication.model.JwtRequest;
import com.waseel.authentication.model.JwtResponse;
import com.waseel.authentication.model.RefreshRequest;
import com.waseel.authentication.service.CustomAuthenticationManager;
import com.waseel.authentication.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(value = "Authentication API", description = "API to Loging in and obtain a token and obtaining a refresh")
@RestController
@RequestMapping
//@CrossOrigin
public class JwtAuthenticationController {

	
	@Autowired
	private CustomAuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "Obtain an access token and a refresh token", response = JwtResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "username or password are not correct"),
			@ApiResponse(code = 400, message = "username or password is missing from request body") })
	@PostMapping(path = "/PBMAuthenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		return ResponseEntity.ok(jwtService.signInUser(authenticationRequest.getUsername()));
	}

	@PostMapping(path = "/PBMAuthenticate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> createAuthenticationTokenFromURLENCODEDrequest(
			@ModelAttribute JwtRequest authenticationRequest) throws Exception {
		return createAuthenticationToken(authenticationRequest);
	}

	@ApiOperation(value = "Get information of the current user", response = Principal.class, authorizations = @Authorization("Bearer"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "invaild access token or expired token") })
	@GetMapping(path = "/users/current")
	public ResponseEntity<?> getCurrentUserInfo(UsernamePasswordAuthenticationToken token) {
		System.out.println("User current call....");
		return ResponseEntity.ok(token.getPrincipal());
	}
	
	@ApiOperation(value = "Get information of the current user", response = Principal.class, authorizations = @Authorization("Bearer"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "invaild access token or expired token") })
	@GetMapping(path = "/token/validate")
	public ResponseEntity<?> getValidateToken(UsernamePasswordAuthenticationToken token) {
		return ResponseEntity.ok(token.getPrincipal());
	}

	@ApiOperation(value = "Verify the access token", authorizations = @Authorization("Bearer"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "invaild access token or expired token") })
	@GetMapping(path = "/check_token")
	public ResponseEntity<?> verifyToken() {
		System.out.println("Come to checktoken...");
		return ResponseEntity.ok("");
	}

	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "Givin a refresh token with its access token, new tokens will be issued.", response = JwtResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "access token or refresh token are invaild"),
			@ApiResponse(code = 400, message = "access token or refresh token is missing from request body") })
	@PostMapping(path = "/refresh")
	public ResponseEntity<?> refershToken(@RequestBody RefreshRequest request) {
		try {
			return ResponseEntity.ok(jwtService.refreshToken(request));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "USER_DISABLED");

		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
