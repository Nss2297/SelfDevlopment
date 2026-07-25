package com.waseel.authentication.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.waseel.authentication.configuration.JwtTokenUtil;
import com.waseel.authentication.model.JwtResponse;
import com.waseel.authentication.model.RefreshRequest;
import com.waseel.authentication.model.portal.enity.AccountToCCHIAssociation;
import com.waseel.authentication.model.portal.enity.RolePrivilege;
import com.waseel.authentication.model.portal.enity.SwitchAccount;
import com.waseel.authentication.model.portal.enity.SwitchUser;
import com.waseel.authentication.repository.AccountToAccountTransactionRepository;
import com.waseel.authentication.repository.AccountToCCHIAssociationRepository;
import com.waseel.authentication.repository.SwitchAccountRepository;
import com.waseel.authentication.repository.SwitchUserRepository;

import io.jsonwebtoken.Claims;
import javassist.NotFoundException;

@Service
public class JwtService implements UserDetailsService {

	private final Logger LOGGER = Logger.getLogger(JwtService.class);
	
	//As of now need only one provider 101 - Waseel PBM
	BigDecimal providerId = BigDecimal.valueOf(101);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AccountToAccountTransactionRepository accountToAccountTransactionRepository;
	@Autowired
	private SwitchAccountRepository switchAccountRepository;
	@Autowired
	public SwitchUserRepository switchUserRepository;
	@Autowired
	private AccountToCCHIAssociationRepository cchiRepository;

	public JwtResponse signInUser(String username) throws NotFoundException, InterruptedException, ExecutionException {
		LOGGER.info("Signing In " + username);
		Optional<SwitchUser> userOp = switchUserRepository.findById(username);

		if (userOp.isPresent()) {
			SwitchUser user = userOp.get();
			LOGGER.info("user :: " + new Gson().toJson(user));
			//BigDecimal providerId = user.getRole().getRolePrivileges().stream().findFirst().get().getId().getSource();
			
			/* Need only 50-59 priviledge and as of now only 101 providerid
			 * here 101 | 50.0 | 101 
			 * source(Provider) 101 is PBM Waseel
			 * Destination(Payer) is constant for us 101 */
			List<SimpleGrantedAuthority> roles = user.getRole().getRolePrivileges().stream()
					.filter(privilege -> privilege.getId().getSource().equals(providerId)
							&& privilege.getId().getDestination().equals(providerId))

					.filter(privilege -> privilege.getId().getTransactionId() >= 50
							&& privilege.getId().getTransactionId() < 60)

					.map(privilege -> new SimpleGrantedAuthority(privilege.getId().getSource() + "|"
							+ privilege.getId().getTransactionId() + "|" + privilege.getId().getDestination()))
					.collect(Collectors.toList());

			roles = roles.stream().distinct().collect(Collectors.toList());

			Optional<SwitchAccount> providerAccountOp = switchAccountRepository.findById(providerId);
			SwitchAccount providerAccount = providerAccountOp.get();
			String fullname;
			if (user.getFirstName() != null) {
				fullname = user.getFirstName();
				if (user.getLastName() != null) {
					fullname = fullname.concat(" ").concat(user.getLastName());
				}
			} else {
				fullname = user.getSwitchUserId();
			}

			String cchiId = null;
			try {
				AccountToCCHIAssociation cchiIdObj = cchiRepository.findByAccountId(providerId);
				cchiId = cchiIdObj != null ? cchiIdObj.getId().getCchiId().getProviderId() + "" : null;
			} catch (Exception e) {
				LOGGER.warn("Provider [" + providerId + "] does not have CCHI ID.", e);
			}

			Map<String, Object> claims = generateJwtClaims(roles,fullname, providerAccount.getName(),
					providerAccount.getCode(), providerId.toString(), cchiId);

			final String accessToken = jwtTokenUtil.generateToken(username, claims);
			final String refershToken = jwtTokenUtil.generateRefreshToken(accessToken, false);
			final Date expiresIn = jwtTokenUtil.getExpirationDateFromToken(accessToken);
			return new JwtResponse(accessToken, refershToken, expiresIn);
		} else {
			LOGGER.info("User not found...");
			throw new NotFoundException("User was not found.");
		}

	}

	public JwtResponse refreshToken(RefreshRequest request) throws Exception {
		if (jwtTokenUtil.validateRefershToken(request.getAccessToken(), request.getRefreshToken())) {
			Claims accessTokenClaims = jwtTokenUtil.getAllClaimsFromToken(request.getAccessToken());
			String username = accessTokenClaims.get("sub", String.class);
			return this.signInUser(username);
		} else
			throw new Exception("Invalid Tokens.");

	}

	private Map<String, Object> generateJwtClaims(List<SimpleGrantedAuthority> roles,
			String fullname, String providerName, String providerCode, String providerId, String cchiId) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("rol", roles);
		//claims.put("payers", payers);
		claims.put("full_name", fullname);
		claims.put("prov", providerName);
		claims.put("prov_code", providerCode);
		claims.put("prov_id", providerId);
		claims.put("cchi_id", cchiId);
		return claims;
	}

	Optional<SwitchUser> switchUser;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		switchUser = switchUserRepository.findById(username);
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

}
