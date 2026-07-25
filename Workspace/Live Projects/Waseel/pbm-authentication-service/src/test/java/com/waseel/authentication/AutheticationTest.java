package com.waseel.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.waseel.authentication.configuration.JwtTokenUtil;
import com.waseel.authentication.controller.JwtAuthenticationController;
import com.waseel.authentication.model.JwtRequest;
import com.waseel.authentication.model.JwtResponse;
import com.waseel.authentication.model.RefreshRequest;
import com.waseel.authentication.model.User;
import com.waseel.authentication.model.portal.enity.AccountToAccountTransaction;
import com.waseel.authentication.model.portal.enity.AccountToAccountTransactionId;
import com.waseel.authentication.model.portal.enity.Role;
import com.waseel.authentication.model.portal.enity.RolePrivilege;
import com.waseel.authentication.model.portal.enity.RolePrivilegeId;
import com.waseel.authentication.model.portal.enity.SwitchAccount;
import com.waseel.authentication.model.portal.enity.SwitchUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutheticationTest {

	@Autowired
	private JwtAuthenticationController jwtAuthenticationController;

	@Autowired
	CrudRepository<SwitchUser, String> switchUserCrudRepository;

	@Autowired
	CrudRepository<SwitchAccount, BigDecimal> switchAccountCrudRepository;

	@Autowired
	CrudRepository<AccountToAccountTransaction, AccountToAccountTransactionId> accountToAccountCrudRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int serverPort;

	@Test
	public void fail_create_authentication_token_no_user_found() {
		JwtRequest authenticationRequest = new JwtRequest("ahmed", "idris");
		try {
			jwtAuthenticationController.createAuthenticationToken(authenticationRequest);
		} catch (Exception e) {
			assertThat(e.getMessage()).isSameAs("INVALID_CREDENTIALS");
		}
	}

	@Test
	public void fail_create_authentication_token_password_not_matching() {
		String username = "ahmed";
		String password = "idris";

		createValidUserInDb(username, password, 1, "1", false);

		JwtRequest authenticationRequest = new JwtRequest(username, "123");

		try {
			jwtAuthenticationController.createAuthenticationToken(authenticationRequest);
		} catch (Exception e) {
			assertThat(e.getMessage()).isSameAs("INVALID_CREDENTIALS");
		}
	}

	@Test
	public void fail_create_authentication_token_user_disabled() throws Exception {
		String username = "ahmed";
		String password = "idris";

		createValidUserInDb(username, password, 1, "0", false);

		JwtRequest authenticationRequest = new JwtRequest(username, password);

		try {
			jwtAuthenticationController.createAuthenticationToken(authenticationRequest);
		} catch (ResponseStatusException e) {

			assertThat(e.getReason()).isSameAs("USER_DISABLED");
		}
	}

	@Test
	public void success_create_authentication_token() throws Exception {
		String username = "ahmed";
		String password = "idris";

		createValidUserInDb(username, password, 1, "1", false);

		JwtRequest authenticationRequest = new JwtRequest(username, password);

		ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(authenticationRequest);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();

		assertThat(jwtResponse.getAccessToken()).isNotNull();
		assertThat(jwtResponse.getExpiresIn()).isNotNull();
		assertThat(jwtResponse.getRefreshToken()).isNotNull();
		assertThat(jwtResponse.getTokenType()).isNotNull();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtResponse.getAccessToken());
		HttpEntity<?> request = new HttpEntity<>(null, headers);

		ResponseEntity<?> validatedToken = restTemplate.exchange("http://localhost:" + serverPort + "/check_token",
				HttpMethod.GET, request, Void.class);
		Assert.assertTrue(validatedToken.getStatusCode().is2xxSuccessful());

	}

	@Test
	public void test_refresh_token_valid() throws Exception {
		String username = "ahmed";
		String password = "idris";

		createValidUserInDb(username, password, 1, "1", false);

		JwtRequest authenticationRequest = new JwtRequest(username, password);

		ResponseEntity<?> response = jwtAuthenticationController.createAuthenticationToken(authenticationRequest);
		JwtResponse jwtResponse = (JwtResponse) response.getBody();

		assertThat(jwtResponse.getAccessToken()).isNotNull();
		assertThat(jwtResponse.getExpiresIn()).isNotNull();
		assertThat(jwtResponse.getRefreshToken()).isNotNull();
		assertThat(jwtResponse.getTokenType()).isNotNull();

		RefreshRequest refreshToken = new RefreshRequest(jwtResponse.getAccessToken(), jwtResponse.getRefreshToken());

		ResponseEntity<?> refreshTokenResponseEntity = jwtAuthenticationController.refershToken(refreshToken);
		JwtResponse refreshedJwtResponse = (JwtResponse) refreshTokenResponseEntity.getBody();

		assertThat(refreshedJwtResponse.getAccessToken()).isNotNull();
		assertThat(refreshedJwtResponse.getExpiresIn()).isNotNull();
		assertThat(refreshedJwtResponse.getRefreshToken()).isNotNull();
		assertThat(refreshedJwtResponse.getTokenType()).isNotNull();

		assertThat(refreshedJwtResponse.getAccessToken()).isNotSameAs(jwtResponse.getAccessToken());
		assertThat(refreshedJwtResponse.getExpiresIn()).isNotSameAs(jwtResponse.getExpiresIn());
		assertThat(refreshedJwtResponse.getRefreshToken()).isNotSameAs(jwtResponse.getRefreshToken());
	
	}

	private void createValidUserInDb(String username, String password, int roleId, String isEnabled,
			boolean isWaseelAdmin) {
		Role role = new Role();
		role.setRoleId(BigDecimal.valueOf(roleId));
		role.setDescription("1");

		RolePrivilege rolePrivilege = new RolePrivilege();
		rolePrivilege.setId(new RolePrivilegeId());
		
		// AS of now need only 101 - Waseel PBM
		rolePrivilege.getId().setDestination(BigDecimal.valueOf(101));
		rolePrivilege.getId().setSource(BigDecimal.valueOf(101));
		rolePrivilege.getId().setTransactionId(Double.valueOf(50.0));
		
		rolePrivilege.getId().setRoleId(BigDecimal.valueOf(roleId));

		Set<RolePrivilege> sets = new HashSet<>();
		sets.add(rolePrivilege);

		role.setRolePrivileges(sets);

		SwitchUser user = new SwitchUser();
		user.setEmail("hashim.ebrahim@waseel.com");
		user.setFirstName("Hashim");
		user.setIsBillable("1");
		user.setIsEnabled(isEnabled);
		user.setIsDeleted("0");
		user.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
		user.setLastName("Ebrahim");
		user.setLastSeenFrom("");
		user.setSwitchUserId(username);
		user.setPassword(createPassword(username, password));
		user.setRole(role);

		SwitchAccount account = new SwitchAccount();
		account.setSwitchAccountId(rolePrivilege.getId().getSource());
		account.setName("Provider");
		account.setCode("Provider");
		account.setCategory("PROVIDER");
		account.setIsEabled("1");

		AccountToAccountTransactionId accountToAccountTransactionId = new AccountToAccountTransactionId(
				rolePrivilege.getId().getSource(), rolePrivilege.getId().getDestination(),
				rolePrivilege.getId().getTransactionId());
		AccountToAccountTransaction accountToAccountTransaction = new AccountToAccountTransaction(
				accountToAccountTransactionId, "1", "1");

		accountToAccountCrudRepository.save(accountToAccountTransaction);
		switchAccountCrudRepository.save(account);
		switchUserCrudRepository.save(user);
	}

	private String createPassword(String username, String password) {
		byte[] digest = null;
		StringBuilder myHash = new StringBuilder();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update((username + password).getBytes());
			digest = md.digest();
			myHash.append(DatatypeConverter.printHexBinary(digest).toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myHash.toString();
	}

}
