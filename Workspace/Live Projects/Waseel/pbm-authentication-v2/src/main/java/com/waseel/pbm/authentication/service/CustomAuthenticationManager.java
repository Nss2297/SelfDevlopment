package com.waseel.pbm.authentication.service;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.waseel.pbm.authentication.model.portal.enity.SwitchAccount;
import com.waseel.pbm.authentication.model.portal.enity.SwitchUser;
import com.waseel.pbm.authentication.repository.SwitchAccountRepository;
import com.waseel.pbm.authentication.repository.SwitchUserRepository;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	public SwitchUserRepository switchUserRepo;

	@Autowired
	SwitchAccountRepository switchAccountRepo;

	@Autowired(required = false)
	CacheManager cacheManager;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";
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

		Optional<SwitchUser> opUser = switchUserRepo.findById(username);

		if (opUser.isEmpty()) {
			if (cacheManager != null)
				cacheManager.getCache("switchuser").evict(username);
			throw new BadCredentialsException("1000");
		}
		SwitchUser user = opUser.get();

		if (!user.getIsDeleted().equals("0")) {
			if (cacheManager != null)
				cacheManager.getCache("switchuser").evict(username);
			throw new BadCredentialsException("1001");
		}
		BigDecimal providerId = user.getRole().getRolePrivileges().iterator().next().getId().getSource();
		Optional<SwitchAccount> account = switchAccountRepo.findById(providerId);
		if (account.isPresent()) {
			if (!account.get().getIsEnabled().equals("1")) {
				if (cacheManager != null)
					cacheManager.getCache("switchuser").evict(username);
				throw new DisabledException("1001");
			}
		}
		if (!user.getPassword().equals(myHash.toString())) {
			if (cacheManager != null)
				cacheManager.getCache("switchuser").evict(username);
			throw new BadCredentialsException("1000");
		}
		if (cacheManager != null) {
			BigDecimal id = new BigDecimal(opUser.get().getRole().getRolePrivileges().parallelStream()
					.mapToInt(x -> x.getId().getSource().intValue()).max().getAsInt());
			cacheManager.getCache("switchaccount").evict(id);
		}
		if (!user.getIsEnabled().equals("1")) {
			if (cacheManager != null)
				cacheManager.getCache("switchuser").evict(username);
			throw new DisabledException("1001");
		}
		return null;
	}

}
