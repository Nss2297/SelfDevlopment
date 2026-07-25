package com.waseel.policy.repository.hira;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.policy.persist.hira.AccountToAccountAssociation;
import com.waseel.policy.persist.hira.AccountToAccountAssociationId;

import feign.Param;

@Repository
public interface AccountToAccountAssociationRepository
		extends CrudRepository<AccountToAccountAssociation, AccountToAccountAssociationId> {

	public Optional<AccountToAccountAssociation> findByIdSourceAndIdDestinationAndIsEnabled(BigDecimal source,
			BigDecimal destination, boolean isEnabled);

	@Query("SELECT model FROM AccountToAccountAssociation model" + " WHERE model.id.source = :providerId "
			+ " AND model.id.destination = :payerId " + " AND model.isEnabled = :isEnabled")
	public AccountToAccountAssociation findByIdSourceAndIdDestinationsAndIsEnabled(
			@Param("providerId") BigDecimal providerId, @Param("payerId") BigDecimal payerId,
			@Param("isEnabled") boolean isEnabled);
}
