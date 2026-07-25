package com.waseel.pbm.authentication.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.authentication.model.portal.enity.SwitchAccount;


public interface SwitchAccountRepository  extends CrudRepository<SwitchAccount, BigDecimal> {

	@Cacheable(value = "switchaccount", key = "#accountid")
	Optional<SwitchAccount> findById(BigDecimal accountid);
	
	@Query(nativeQuery = true, value = "select max(sa.\"SwitchAccountId\")+1 from \"SwitchAccount\" sa where sa.\"SwitchAccountId\" < 99999")
	public BigDecimal makeSwitchAccountIdBySequence();
	
	@Query(value = "select count(*) from SwitchAccount sa where sa.code=?1")
	public Integer getCodeCount(String code);
	
	@Query("SELECT sa FROM SwitchAccount sa " 
			+ "WHERE sa.switchAccountId = :accountId "
			+ "AND sa.isEnabled = :isEnabled " 
			+ "AND LOWER(sa.category) = LOWER(:category)")
	Optional<SwitchAccount> findBySwitchAccountIdAndIsEnabledAndCategory(@Param("accountId") BigDecimal accountId,
			@Param("isEnabled") String isEnabled, @Param("category") String category);

}
