package com.waseel.prescription.repository.hira;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.waseel.prescription.persist.hira.AccountToAccountAssociation;
import com.waseel.prescription.persist.hira.AccountToAccountAssociationId;

import feign.Param;

@Repository
public interface AccountToAccountAssociationRepository
		extends CrudRepository<AccountToAccountAssociation, AccountToAccountAssociationId> {

	public Optional<AccountToAccountAssociation> findByIdSourceAndIdDestinationAndIsEnabled(BigDecimal source,
			BigDecimal destination, boolean isEnabled);
	
	@Query("SELECT model FROM AccountToAccountAssociation model"
			+ " WHERE model.id.source = :providerId "
			+ " AND model.id.destination IN (:payerIds)"
			+ " AND model.isEnabled = :isEnabled")
	public List<AccountToAccountAssociation> findByIdSourceAndIdDestinationsAndIsEnabled(
			@Param("providerId") BigDecimal providerId,
			@Param("payerIds") List<BigDecimal> payerIds,
			@Param("isEnabled") boolean isEnabled);
}
