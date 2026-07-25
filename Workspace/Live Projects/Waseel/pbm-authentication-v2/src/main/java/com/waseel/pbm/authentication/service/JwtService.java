package com.waseel.pbm.authentication.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.waseel.pbm.authentication.configuration.JwtTokenUtil;
import com.waseel.pbm.authentication.model.JwtResponse;
import com.waseel.pbm.authentication.model.OneTimeAccessTokenRequest;
import com.waseel.pbm.authentication.model.PbmGrantedAuthority;
import com.waseel.pbm.authentication.model.User;
import com.waseel.pbm.authentication.model.portal.enity.RolePrivilege;
import com.waseel.pbm.authentication.model.portal.enity.SwitchAccount;
import com.waseel.pbm.authentication.model.portal.enity.SwitchUser;
import com.waseel.pbm.authentication.repository.SwitchAccountRepository;
import com.waseel.pbm.authentication.repository.SwitchUserRepository;

import io.jsonwebtoken.Claims;

@Service
public class JwtService implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(JwtService.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private Environment environment;

	@Autowired
	private SwitchAccountRepository switchAccountRepository;

	@Autowired
	public SwitchUserRepository switchUserRepository;

	public JwtResponse signInUser(String username, HttpServletResponse response) throws NotFoundException {
		LOGGER.info("Signing In " + username);
		Optional<SwitchUser> userOp = switchUserRepository.findById(username);
		if (userOp.isPresent()) {
			SwitchUser user = userOp.get();
			BigDecimal sourceId = user
					.getRole().getRolePrivileges().stream().sorted((privilege1, privilege2) -> privilege2.getId()
							.getSource().compareTo(privilege1.getId().getSource()))
					.findFirst().get().getId().getSource();
			Map<BigDecimal, String> destinations = getDestinationsList(user.getRole().getRolePrivileges().stream()
					.filter(privilege -> privilege.getId().getTransactionId() >= 50
							&& privilege.getId().getTransactionId() < 56)
					.map(privilege -> privilege.getId().getDestination()).collect(Collectors.toList()));
			List<PbmGrantedAuthority> roles = new ArrayList<>();
			destinations.forEach((destinationId, data) -> {
				List<String> transactions = user.getRole().getRolePrivileges().stream()
						.filter(privilege -> privilege.getId().getDestination().equals(destinationId)
								|| privilege.getId().getSource().equals(destinationId))
						.map(privilege -> getRoleOfTransaction(privilege.getId().getTransactionId()))
						.filter(transaction -> transaction != null).collect(Collectors.toList());
				if (!transactions.isEmpty()) {
					transactions.forEach(
							transaction -> roles.add(new PbmGrantedAuthority(destinationId.toString(), transaction)));
				}
			});
			if (roles.isEmpty()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "NOT_ENOUGH_PRIVILEGES");
			}
			Optional<SwitchAccount> switchAccountOp = switchAccountRepository.findById(sourceId);
			if (switchAccountOp.isPresent()) {
				SwitchAccount switchAccount = switchAccountOp.get();
				String fullname;
				if (user.getFirstName() != null) {
					fullname = user.getFirstName();
					if (user.getLastName() != null) {
						fullname = fullname.concat(" ").concat(user.getLastName());
					}
				} else {
					fullname = user.getSwitchUserId();
				}
				Map<String, Object> claims = generateJwtClaims(roles, fullname, switchAccount.getName(),
						switchAccount.getCode(), sourceId.toString(), switchAccount.getCategory(), user.getEmail());
				final String accessToken = jwtTokenUtil.generateToken(username, claims);
				final String refreshToken = jwtTokenUtil.generateRefreshToken(accessToken);
				final Date expiresIn = jwtTokenUtil.getExpirationDateFromToken(accessToken);
				JwtResponse jwtResponse = new JwtResponse(accessToken, expiresIn);
				Cookie cookie = new Cookie("refresh_token", refreshToken);
				cookie.setHttpOnly(true);
				cookie.setSecure(!Arrays.asList(environment.getActiveProfiles()).contains("dev"));
				response.addCookie(cookie);
				return jwtResponse;
			}
		}
		throw new NotFoundException();
	}

	@SuppressWarnings("unchecked")
	public JwtResponse refreshToken(String refreshToken, HttpServletResponse response) throws Exception {
		if (Boolean.TRUE.equals(jwtTokenUtil.validateRefreshToken(refreshToken))) {
			Claims accessTokenClaims = jwtTokenUtil
					.getAllClaimsFromToken(jwtTokenUtil.getClaimFromToken(refreshToken, Claims::getSubject));
			String username = accessTokenClaims.get("sub", String.class);

			List<LinkedHashMap<String, String>> authorities = accessTokenClaims.get("rol", List.class);
			String accName = accessTokenClaims.get("acc_name", String.class);
			String userFullName = accessTokenClaims.get("user_full_name", String.class);
			String accId = accessTokenClaims.get("acc_id", String.class);
			String accCode = accessTokenClaims.get("acc_code", String.class);
			String accCategory = accessTokenClaims.get("acc_category", String.class);
			String email = accessTokenClaims.get("user_email", String.class);
			User user = new User(username, authorities.parallelStream()
					.map(x -> new SimpleGrantedAuthority(x.get("authority"))).collect(Collectors.toList()),
					userFullName, accName, accId, accCode, accCategory, email);
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			return this.signInUser(username, response);
		} else
			throw new Exception("Invalid Tokens.");

	}

	private Map<BigDecimal, String> getDestinationsList(List<BigDecimal> destinationIds) {
		Map<BigDecimal, String> destinations = new HashMap<>();
		destinationIds.forEach(destinationId -> {
			if (!destinations.containsKey(destinationId)) {
				Optional<SwitchAccount> opAccount = switchAccountRepository.findById(destinationId);
				if (opAccount.isPresent()) {
					SwitchAccount account = opAccount.get();
					destinations.put(destinationId,
							account.getName() + "," + account.getArabicName() + "," + account.getPayerCategory() + ","
									+ account.getOrganization().getOrganizationName() + ","
									+ account.getOrganization().getOrganizationId().toString());
				}
			}
		});
		return destinations;
	}

	private Map<String, Object> generateJwtClaims(List<? extends GrantedAuthority> roles, String fullName,
			String accountName, String accountCode, String accountId, String accountCategory, String email) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("rol", roles);
		claims.put("user_full_name", fullName);
		claims.put("acc_name", accountName);
		claims.put("acc_code", accountCode);
		claims.put("acc_id", accountId);
		claims.put("acc_category", accountCategory);
		claims.put("user_email", email);
		return claims;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SwitchUser> switchUser = switchUserRepository.findById(username);
		if (switchUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		List<RolePrivilege> userRights = new ArrayList<>(switchUser.get().getRole().getRolePrivileges());

		return new org.springframework.security.core.userdetails.User(switchUser.get().getSwitchUserId(),
				switchUser.get().getPassword(),
				userRights.parallelStream().map(x -> new SimpleGrantedAuthority(x.getId().getSource().toString() + "|"
						+ x.getId().getTransactionId().toString() + "|" + x.getId().getDestination().toString()))
						.collect(Collectors.toList()));
	}

	/*
	 * standard transaction table: 50.2 Waseel PBM Admin 50.21 PBM Customization
	 * 50.211 Customization Request 50.2111 Medical Customization 50.2112 Business
	 * Customization 50.212 Customization Upload 50.22 IDF Management 50.23 FDB
	 * Management 50.24 SFDA Management 50.3 Business Rule Administration 50.31 Drug
	 * Formulary Management 51 Waseel ePrescription 51.1 PBM Prescription 51.11 New
	 * Prescription 51.12 FollowUp Prescription 51.13 Prescription Cancellation
	 * 51.14 Prescription Inquiry 51.141 Detail Inquiry 51.142 Summary Inquiry 51.15
	 * Prescription Dispense 51.4 View Prescription 51.5 Edit Prescription Decision
	 * 51.6 Override Dss Decision 52 Business Rules 52.1 Eligibility 52.2 Policy
	 * Consumption 52.3 Drug Formulary 53 PBM Notifications 53.1 Prescription
	 * Notification 50.32 Exclusion Management 50.214 Drug To Gender Customization
	 */

	private String getRoleOfTransaction(Double transactionId) {
		if (transactionId.equals(50.2)) {
			return "PBM_ADMIN";
		} else if (transactionId.equals(50.21d)) {
			return "PBM_CUSTOMIZATION";
		} else if (transactionId.equals(50.211d)) {
			return "CUSTOMIZATION_REQUEST";
		} else if (transactionId.equals(50.2111d)) {
			return "MEDICAL_CUSTOMIZATION";
		} else if (transactionId.equals(50.2112d)) {
			return "BUSINESS_CUSTOMIZATION";
		} else if (transactionId.equals(50.212d)) {
			return "CUSTOMIZATION_UPLOAD";
		} else if (transactionId.equals(50.2121d)) {
			return "GENDER_CUSTOMIZATION_UPLOAD";
		} else if (transactionId.equals(50.2122d)) {
			return "DRUG_CUSTOMIZATION_UPLOAD";
		} else if (transactionId.equals(50.2123d)) {
			return "AGE_CUSTOMIZATION_UPLOAD";
		} else if (transactionId.equals(50.2124d)) {
			return "DUPLICATE_THERAPY_CUSTOMIZATION_UPLOAD";
		} else if (transactionId.equals(50.213d)) {
			return "DRUG_TO_DIAGNOSIS_CUSTOMIZATION";
		} else if (transactionId.equals(50.214d)) {
			return "DRUG_TO_GENDER_CUSTOMIZATION";
		} else if (transactionId.equals(50.215d)) {
			return "DRUG_TO_AGE_CUSTOMIZATION";
		} else if (transactionId.equals(50.216d)) {
			return "DRUG_TO_DRUG_CUSTOMIZATION";
		} else if (transactionId.equals(50.217d)) {
			return "DUPLICATE_THERAPY_CUSTOMIZATION";
		} else if (transactionId.equals(50.22d)) {
			return "IDF_MANAGEMENT";
		} else if (transactionId.equals(50.23d)) {
			return "FDB_MANAGEMENT";
		} else if (transactionId.equals(50.24d)) {
			return "SFDA_MANAGEMENT";
		} else if (transactionId.equals(50.3d)) {
			return "BUSINESS_RULE_ADMINISTRATION";
		} else if (transactionId.equals(50.31d)) {
			return "DRUG_FORMULARY_MANAGEMENT";
		} else if (transactionId.equals(50.32d)) {
			return "EXCLUSION_MANAGEMENT";
		} else if (transactionId.equals(50.321d)) {
			return "HIGH_COST_EXCLUSION";
		} else if (transactionId.equals(50.322d)) {
			return "NETWORK_EXCLUSION";
		} else if (transactionId.equals(50.323d)) {
			return "PROVIDER_EXCLUSION";
		} else if (transactionId.equals(50.324d)) {
			return "SPECIALTY_EXCLUSION";
		} else if (transactionId.equals(50.33d)) {
			return "MEMBER_MANAGEMENT";
		} else if (transactionId.equals(51d)) {
			return "PBM_PRESCRIPTION";
		} else if (transactionId.equals(51.1d)) {
			return "PRESCRIPTION_SUBMISSION";
		} else if (transactionId.equals(51.11d)) {
			return "NEW_PRESCRIPTION";
		} else if (transactionId.equals(51.12d)) {
			return "FOLLOW_UP_PRESCRIPTION";
		} else if (transactionId.equals(51.13d)) {
			return "PRESCRIPTION_CANCELLATION";
		} else if (transactionId.equals(51.2d)) {
			return "PRESCRIPTION_INQUIRY";
		} else if (transactionId.equals(51.21d)) {
			return "DETAIL_INQUIRY";
		} else if (transactionId.equals(51.22d)) {
			return "SUMMARY_INQUIRY";
		} else if (transactionId.equals(51.3d)) {
			return "PRESCRIPTION_DISPENSE";
		} else if (transactionId.equals(51.4d)) {
			return "VIEW_PRESCRIPTION";
		} else if (transactionId.equals(51.5d)) {
			return "EDIT_PRESCRIPTION_DECISION";
		} else if (transactionId.equals(51.6d)) {
			return "OVERRIDE_MEDICATION";
		} else if (transactionId.equals(53d)) {
			return "PBM_NOTIFICATIONS";
		} else if (transactionId.equals(53.1d)) {
			return "PRESCRIPTION_NOTIFICATION";
		}
		return null;
	}

	public JwtResponse generateLimitedAccessToken(OneTimeAccessTokenRequest request, String clientId) {
		final String accessToken = jwtTokenUtil.generateLimitedAccessToken(request, clientId);
		final Date expiresIn = jwtTokenUtil.getExpirationDateFromToken(accessToken);
		JwtResponse response = new JwtResponse(accessToken, expiresIn);
		return response;
	}
}