package com.waseel.authentication.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.waseel.authentication.model.portal.enity.SwitchAccount;


public interface SwitchAccountRepository  extends CrudRepository<SwitchAccount, BigDecimal> {

	@Cacheable(value = "switchaccount", key = "#accountid")
	Optional<SwitchAccount> findById(BigDecimal accountid);
}
