package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderExclusionAssc;

@Repository
public interface ProviderExclusionAsscRepository extends JpaRepository<ProviderExclusionAssc, Long> {

	@Query("SELECT new com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderExclusionAssc(pea.providerExclusionAsscId, pea.providerId, pea.exclusionId, pea.providerName, pea.isEnabled, pea.payerId) FROM ProviderExclusionAssc pea, DrugExclusionMetadata demd WHERE pea.providerId= :providerId AND pea.exclusionId=demd.exclusionId AND demd.payerId= :payerId AND pea.payerId= :payerId AND demd.isDeleted= :isDeleted AND pea.isEnabled= :isEnabled")
	List<ProviderExclusionAssc> findByProviderIdAndPayerIdAndIsEnabledAndIsDeleted(
			@Param("providerId") Long providerId, @Param("payerId") Long payerId, @Param("isEnabled") Boolean isEnabled,
			@Param("isDeleted") Boolean isDeleted);
}
