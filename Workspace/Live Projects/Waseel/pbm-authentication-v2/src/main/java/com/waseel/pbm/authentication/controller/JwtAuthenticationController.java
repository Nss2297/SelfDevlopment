package com.waseel.pbm.authentication.controller;

import java.security.Principal;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.waseel.pbm.authentication.model.JwtRequest;
import com.waseel.pbm.authentication.model.JwtResponse;
import com.waseel.pbm.authentication.model.OneTimeAccessTokenRequest;
import com.waseel.pbm.authentication.model.SsoRequest;
import com.waseel.pbm.authentication.service.CustomAuthenticationManager;
import com.waseel.pbm.authentication.service.JwtService;
import com.waseel.pbm.authentication.service.SsoService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping
@CrossOrigin(originPatterns = "*")
public class JwtAuthenticationController {

	@Autowired
	private CustomAuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private SsoService ssoService;

	@Operation(summary = "Obtain an access token and a refresh token", description = "This API is used to obtain an access token or a refresh token.", responses = {
			@ApiResponse(responseCode = "200", description = "Success|OK", content = {
					@Content(schema = @Schema(implementation = JwtResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "username or password are not correct"),
			@ApiResponse(responseCode = "400", description = "username or password is missing from request body") })
	@PostMapping(path = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			HttpServletResponse response) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		return ResponseEntity.ok(jwtService.signInUser(authenticationRequest.getUsername(), response));
	}

	@Hidden
	@PostMapping(path = "/signIn", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> createAuthenticationTokenFromURLENCODEDrequest(
			@ModelAttribute JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {
		return createAuthenticationToken(authenticationRequest, response);
	}

	@Hidden
	@PostMapping(path = "/sso/authenticate")
	public ResponseEntity<?> createAuthenticationTokenFromSsoToken(@RequestBody SsoRequest ssoRequest,
			HttpServletResponse response) throws NotFoundException, InterruptedException, ExecutionException {
		return ResponseEntity.ok(ssoService.verifyAndSignIn(ssoRequest.getBearerToken(), response));
	}

	@Operation(summary = "Get information of the current user", responses = {
			@ApiResponse(responseCode = "200", description = "Success|OK", content = {
					@Content(schema = @Schema(implementation = Principal.class)) }),
			@ApiResponse(responseCode = "401", description = "invaild access token or expired token") })
	@GetMapping(path = "/users/current")
	public ResponseEntity<?> getCurrentUserInfo(UsernamePasswordAuthenticationToken token) {
		return ResponseEntity.ok(token.getPrincipal());
	}

	@Hidden
	@PostMapping(path = "/users/current")
	public ResponseEntity<?> getCurrentUserInfoAsPostRequest(UsernamePasswordAuthenticationToken token) {
		return ResponseEntity.ok(token.getPrincipal());
	}

	@Operation(summary = "Get information of the current user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success|OK", content = {
					@Content(schema = @Schema(implementation = Principal.class)) }),
			@ApiResponse(responseCode = "401", description = "invaild access token or expired token") })
	@Hidden
	@GetMapping(path = "/token/validate")
	public ResponseEntity<?> getValidateToken(UsernamePasswordAuthenticationToken token) {

		return ResponseEntity.ok(token.getPrincipal());
	}

	@Operation(summary = "Verify the access token")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success|OK"),
			@ApiResponse(responseCode = "401", description = "invaild access token or expired token") })
	@GetMapping(path = "/check_token")
	public ResponseEntity<?> verifyToken() {
		return ResponseEntity.ok("");
	}

	@Operation(summary = "Giving a refresh token with its access token, new tokens will be issued.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success|OK", content = {
					@Content(schema = @Schema(implementation = JwtResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "access token or refresh token are invaild") })
	@GetMapping(path = "/refresh")
	@CrossOrigin
	public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token") String refreshToken,
			HttpServletResponse response) {
		try {
			return ResponseEntity.ok(jwtService.refreshToken(refreshToken, response));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}

	@Operation(summary = "Sign Out")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success|OK", content = {
			@Content(schema = @Schema(implementation = JwtResponse.class)) }) })
	@GetMapping(path = "/signOut")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("refresh_token", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return ResponseEntity.ok().build();
	}

	@Hidden
	@PostMapping("/token/limited")
	public JwtResponse generateLimitedAccessToken(@RequestBody OneTimeAccessTokenRequest request,
			Authentication authentication) {
		return jwtService.generateLimitedAccessToken(request, authentication.getName());
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
