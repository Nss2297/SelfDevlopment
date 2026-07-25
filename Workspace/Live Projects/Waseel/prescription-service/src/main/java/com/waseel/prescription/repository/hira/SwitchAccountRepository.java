package com.waseel.prescription.repository.hira;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.prescription.persist.hira.SwitchAccount;

public interface SwitchAccountRepository extends CrudRepository<SwitchAccount, BigDecimal> {

	@Query("SELECT sa FROM SwitchAccount sa " + "WHERE sa.switchAccountId = :accountId "
			+ "AND sa.isEnabled = :isEnabled " + "AND LOWER(sa.category) = LOWER(:category)")
	Optional<SwitchAccount> findBySwitchAccountIdAndIsEnabledAndCategory(@Param("accountId") BigDecimal accountId,
			@Param("isEnabled") String isEnabled, @Param("category") String category);
}
