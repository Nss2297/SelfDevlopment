package com.waseel.pbm.pbmadminservice.repository.mdss;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.pbm.pbmadminservice.model.customization.PayerConfigModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.PayerConfig;
import com.waseel.pbm.pbmadminservice.persist.mdss.PayerConfigId;

public interface PayerConfigRepository
		extends JpaRepository<PayerConfig, PayerConfigId>, JpaSpecificationExecutor<PayerConfig> {

	Optional<PayerConfig> findByIdPayerIdAndIdIsEnabled(String payerId, Character isEnabled);

	List<PayerConfig> findByIdIsEnabled(Character isEnabled);
	
	@Query(value = "SELECT excelPayerIdSet.COLUMN_VALUE as payerId, "
		            + "CASE "
		            + "    WHEN mdssPayerConfigSet.\"PayerId\" IS NULL OR mdssPayerConfigSet.\"isEnabled\" = '0' THEN 0 "
		            + "    ELSE 1 "
		            + "END as isValid "
		            + "FROM TABLE( "
		            + "    SYS.ODCIVARCHAR2LIST( "
		            + "        :payerIds "
		            + "    ) "
		            + ") excelPayerIdSet "
		            + "LEFT JOIN MDSS.\"PayerConfig\" mdssPayerConfigSet on"
		            + " excelPayerIdSet.COLUMN_VALUE = mdssPayerConfigSet.\"PayerId\"",
	            nativeQuery = true)
	List<PayerConfigModel> findByPayerIds(@Param("payerIds") List<String> payerIds);
}
