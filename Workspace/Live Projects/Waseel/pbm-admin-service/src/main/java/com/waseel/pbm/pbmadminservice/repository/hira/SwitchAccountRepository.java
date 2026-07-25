package com.waseel.pbm.pbmadminservice.repository.hira;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;

public interface SwitchAccountRepository extends CrudRepository<SwitchAccount, BigDecimal> {
	
	Optional<SwitchAccount> findBySwitchAccountIdAndIsEnabledAndCategoryIgnoreCase(BigDecimal switchAccountId,
			String isEnabled, String category);
}
